Feature: Delete Service
  As a business owner, I wish to delete a service so that I can keep my customers up to date.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system
    Given the system's time and date is "2020-10-01+14:00"

  Scenario Outline: Delete a service successfully
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | wash  |       30 |             0 |                0 |
      | color |       50 |            20 |               10 |
      | cut   |       40 |             0 |                0 |
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following appointments exist in the system:
      | customer  | serviceName | date       | startTime | endTime |
      | customer1 | wash        | 2020-09-01 | 13:00     | 13:30   |
      | customer2 | wash        | 2019-09-01 | 13:00     | 13:30   |
      | customer2 | cut         | 2019-09-02 | 13:00     | 13:40   |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the deletion of service "<name>"
    Then the service "<name>" shall not exist in the system
    Then the number of services in the system shall be "2"
    Then the number of appointments in the system with service "<name>" shall be "0"
    Then the number of appointments in the system shall be "<numberOfAppointments>"

    Examples: 
      | name  | numberOfAppointments |
      | wash  |                    1 |
      | color |                    3 |

  Scenario Outline: Delete a service included in service combos
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following service combos exist in the system:
      | name         | mainService | services                | mandatory             |
      | Cut-Regular  | cut         | wash,dry,cut            | false,false,true      |
      | Wash-Deluxe  | wash        | wash,dry,cut,extensions | true,false,true,false |
      | Wash-Regular | wash        | wash,dry                | true,true             |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the deletion of service "<name>"
    Then the service "<name>" shall not exist in the system
    Then the number of services in the system shall be "5"
    Then the service combos "<deletedServiceCombos>" shall not exist in the system
    Then the service combos "<modifiedServiceCombo>" shall not contain service "<name>"
    Then the number of service combos in the system shall be "<numberOfServiceCombos>"

    Examples: 
      | name | deletedServiceCombos     | modifiedServiceCombo | numberOfServiceCombos |
      | cut  | Cut-Regular              | Wash-Deluxe          |                     2 |
      | wash | Wash-Deluxe,Wash-Regular | Cut-Regular          |                     1 |

  Scenario Outline: Delete a service with future appointments
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |       30 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following appointments exist in the system:
      | customer  | serviceName | date       | startTime | endTime |
      | customer1 | extensions  | 2020-10-02 | 13:00     | 13:50   |
      | customer1 | wash        | 2020-09-01 | 13:00     | 13:30   |
      | customer2 | wash        | 2020-10-03 | 13:00     | 13:30   |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the deletion of service "<name>"
    Then an error message with content "<error>" shall be raised
    Then the service "<name>" shall exist in the system
    Then the number of services in the system shall be "2"
    Then the number of appointments in the system with service "<name>" shall be "<numberOfAppointments>"
    Then the number of appointments in the system shall be "3"

    Examples: 
      | name       | numberOfAppointments | error                                    |
      | wash       |                    2 | The service contains future appointments |
      | extensions |                    1 | The service contains future appointments |

  Scenario Outline: Unauthorized attempt to delete a service
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | wash  |       30 |             0 |                0 |
      | color |       50 |            20 |               10 |
      | cut   |       40 |             0 |                0 |
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following appointments exist in the system:
      | customer  | serviceName | date       | startTime | endTime |
      | customer1 | wash        | 2020-09-01 | 13:00     | 13:30   |
      | customer2 | wash        | 2019-09-01 | 13:00     | 13:30   |
      | customer2 | cut         | 2019-09-02 | 13:00     | 13:30   |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the deletion of service "wash"
    Then an error message with content "<error>" shall be raised
    Then the service "wash" shall exist in the system
    Then the number of services in the system shall be "3"
    Then the number of appointments in the system with service "wash" shall be "2"
    Then the number of appointments in the system shall be "3"

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

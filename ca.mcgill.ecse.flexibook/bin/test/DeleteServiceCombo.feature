Feature: Delete Service Combo
  As a business owner, I wish to delete a service combo so that I can keep my customers up to date.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system
    Given the system's time and date is "2020-10-01+14:00"

  Scenario Outline: Delete service combo successfully
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |       20 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       30 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following service combos exist in the system:
      | name       | mainService | services       | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry   | true,true,true   |
      | Wash-Dry   | wash        | wash,dry       | true,false       |
      | Wash-Color | color       | wash,dry,color | false,false,true |
    Given the following appointments exist in the system:
      | customer  | serviceName | selectedComboItems | date       | startTime | endTime |
      | customer1 | Cut-Normal  | wash,cut,dry       | 2020-09-01 | 13:00     | 14:00   |
      | customer2 | Cut-Normal  | wash,cut,dry       | 2019-09-03 | 13:00     | 14:00   |
      | customer2 | Wash-Dry    | wash,dry           | 2019-09-12 | 13:00     | 13:30   |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the deletion of service combo "<name>"
    Then the service combo "<name>" shall not exist in the system
    Then the number of service combos in the system shall be "2"
    Then the number of appointments in the system with service "<name>" shall be "0"
    Then the number of appointments in the system shall be "<numberOfAppointments>"

    Examples: 
      | name       | numberOfAppointments |
      | Wash-Dry   |                    2 |
      | Wash-Color |                    3 |
      | Cut-Normal |                    1 |

  Scenario Outline: Delete service combo with future appointments
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
      | name       | mainService | services       | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry   | true,true,true   |
      | Wash-Dry   | wash        | wash,dry       | true,false       |
      | Wash-Color | color       | wash,dry,color | false,false,true |
    Given the following appointments exist in the system:
      | customer  | serviceName | selectedComboItems | date       | startTime | endTime |
      | customer1 | Cut-Normal  | wash,cut,dry       | 2020-11-01 | 13:00     | 14:00   |
      | customer2 | Cut-Normal  | wash,cut,dry       | 2020-09-03 | 13:00     | 14:00   |
      | customer2 | Wash-Dry    | wash,dry           | 2020-10-10 | 13:00     | 13:30   |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the deletion of service combo "<name>"
    Then an error message with content "<error>" shall be raised
    Then the service combo "<name>" shall exist in the system
    Then the number of service combos in the system shall be "3"
    Then the number of appointments in the system with service "<name>" shall be "<numberOfAppointments>"
    Then the number of appointments in the system shall be "3"

    Examples: 
      | name       | numberOfAppointments | error                                            |
      | Cut-Normal |                    2 | Service combo Cut-Normal has future appointments |
      | Wash-Dry   |                    1 | Service combo Wash-Dry has future appointments   |

  Scenario Outline: Unauthorized attempt to delete a service combo
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
      | name       | mainService | services       | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry   | true,true,true   |
      | Wash-Dry   | wash        | wash,dry       | true,false       |
      | Wash-Color | color       | wash,dry,color | false,false,true |
    Given the following appointments exist in the system:
      | customer  | serviceName | selectedComboItems | date       | startTime | endTime |
      | customer1 | Cut-Normal  | wash,cut,dry       | 2020-09-01 | 13:00     | 14:00   |
      | customer2 | Cut-Normal  | wash,cut,dry       | 2019-09-03 | 13:00     | 14:00   |
      | customer2 | Wash-Dry    | wash,dry           | 2019-09-12 | 13:00     | 13:30   |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the deletion of service combo "Cut-Normal"
    Then an error message with content "<error>" shall be raised
    Then the service combo "Cut-Normal" shall exist in the system
    Then the number of service combos in the system shall be "3"
    Then the number of appointments in the system with service "Cut-Normal" shall be "2"
    Then the number of appointments in the system shall be "3"

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

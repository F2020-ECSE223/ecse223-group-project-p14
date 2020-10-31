Feature: Define Service Combo
  As a business owner, I wish to add service combos to my business so that my customers can make appointments for them.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system

  Scenario Outline: Define a service combo successfully
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the definition of a service combo "<name>" with main service "<mainService>", services "<services>" and mandatory setting "<mandatory>"
    Then the service combo "<name>" shall exist in the system
    Then the service combo "<name>" shall contain the services "<services>" with mandatory setting "<mandatory>"
    Then the main service of the service combo "<name>" shall be "<mainService>"
    Then the service "<mainService>" in service combo "<name>" shall be mandatory
    Then the number of service combos in the system shall be "1"

    Examples: 
      | name        | mainService | services     | mandatory        |
      | Cut-Regular | cut         | wash,dry,cut | false,false,true |
      | Wash-Cut    | wash        | wash,dry,cut | true,true,false  |

  Scenario Outline: Define a service combo with invalid parameters
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name        | mainService | services     | mandatory        |
      | Cut-Regular | cut         | wash,dry,cut | false,false,true |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the definition of a service combo "<name>" with main service "<mainService>", services "<services>" and mandatory setting "<mandatory>"
    Then an error message with content "<error>" shall be raised
    Then the service combo "<name>" shall not exist in the system
    Then the number of service combos in the system shall be "1"

    Examples: 
      | name           | mainService | services                | mandatory            | error                                            |
      | Cut-Highlight  | highlight   | wash,dry,cut            | false,false,true     | Service highlight does not exist                 |
      | Cut-Highlights | highlights  | wash,dry,cut            | false,false,true     | Main service must be included in the services    |
      | Cut-Extensions | extensions  | wash,dry,cut,extensions | true,true,true,false | Main service must be mandatory                   |
      | Cut-Normal     | cut         | cut                     | true                 | A service Combo must contain at least 2 services |
      | Cut-Lunch      | cut         | wash,dry,cut,lunch      | true,true,true,false | Service lunch does not exist                     |

  Scenario Outline: Define an existing service combo
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name        | mainService | services     | mandatory        |
      | Cut-Regular | cut         | wash,dry,cut | false,false,true |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the definition of a service combo "<name>" with main service "<mainService>", services "<services>" and mandatory setting "<mandatory>"
    Then an error message with content "<error>" shall be raised
    Then the service combo "<name>" shall preserve the following properties:
      | name        | mainService | services     | mandatory        |
      | Cut-Regular | cut         | wash,dry,cut | false,false,true |
    Then the number of service combos in the system shall be "1"

    Examples: 
      | name        | mainService | services | mandatory  | error                                    |
      | Cut-Regular | cut         | wash,cut | false,true | Service combo Cut-Regular already exists |

  Scenario Outline: Unauthorized attempt to define a service combo
    Given the following customers exist in the system:
      | username  | password |
      | customer1 |  1234567 |
      | customer2 |  8901234 |
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the definition of a service combo "Cut-Regular" with main service "cut", services "wash,dry,cut" and mandatory setting "false,false,true"
    Then an error message with content "<error>" shall be raised
    Then the service combo "Cut-Regular" shall not exist in the system
    Then the number of service combos in the system shall be "0"

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

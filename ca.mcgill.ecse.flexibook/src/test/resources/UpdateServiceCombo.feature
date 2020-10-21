Feature: Update Service Combo
  As a business owner, I wish to update my existing services combo in my business so that I can keep my customers up to date.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system

  Scenario Outline: Update a service combo successfully
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name       | mainService | services     | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry | false,true,false |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the update of service combo "Cut-Normal" to name "<name>", main service "<mainService>" and services "<services>" and mandatory setting "<mandatory>"
    Then the service combo "Cut-Normal" shall be updated to name "<name>"
    Then the service combo "<name>" shall contain the services "<services>" with mandatory setting "<mandatory>"
    Then the main service of the service combo "<name>" shall be "<mainService>"
    Then the service "<mainService>" in service combo "<name>" shall be mandatory

    Examples: 
      | name        | mainService | services                | mandatory             |
      | Cut-Normal  | cut         | wash,cut,dry            | false,true,true       |
      | Cut-Special | cut         | wash,cut,dry,highlights | false,true,false,true |
      | Wash-Dry    | wash        | wash,dry                | true,true             |

  Scenario Outline: Update a service combo with invalid parameters
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | highlights |       90 |            50 |               40 |
      | color      |       75 |            45 |               30 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name       | mainService | services     | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry | false,true,false |
      | Wash-Dry   | wash        | wash,dry     | true,false       |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the update of service combo "Cut-Normal" to name "<name>", main service "<mainService>" and services "<services>" and mandatory setting "<mandatory>"
    Then an error message with content "<error>" shall be raised
    Then the service combo "Cut-Normal" shall preserve the following properties:
      | name       | mainService | services     | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry | false,true,false |

    Examples: 
      | name           | mainService | services                | mandatory            | error                                         |
      | Cut-Highlight  | highlight   | wash,dry,cut            | false,false,true     | Service highlight does not exist              |
      | Cut-Highlights | highlights  | wash,dry,cut            | false,false,true     | Main service must be included in the services |
      | Cut-Normal     | cut         | cut                     | true                 | A service Combo must have at least 2 services |
      | Cut-Extensions | extensions  | wash,dry,cut,extensions | true,true,true,false | Main service must be mandatory                |
      | Cut-Lunch      | cut         | wash,dry,cut,lunch      | true,true,true,false | Service lunch does not exist                  |
      | Wash-Dry       | wash        | wash,dry                | true,true            | Service combo Wash-Dry already exists         |

  Scenario Outline: Unauthorized attempt to update a service combo
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
    Given the following service combos exist in the system:
      | name       | mainService | services     | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry | false,true,false |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the update of service combo "Cut-Normal" to name "Cut-Normal", main service "service" and services "cut,wash" and mandatory setting "true,true"
    Then an error message with content "<error>" shall be raised
    Then the service combo "Cut-Normal" shall preserve the following properties:
      | name       | mainService | services     | mandatory        |
      | Cut-Normal | cut         | wash,cut,dry | false,true,false |

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

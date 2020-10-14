Feature: Add Service
  As a business owner, I wish to add services to my business so that my customers can make appointments for them.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system

  Scenario Outline: Add a service successfully
    Given the Owner with username "owner" is logged in
    When "owner" initiates the addition of the service "<name>" with duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then the service "<name>" shall exist in the system
    Then the service "<name>" shall have duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then the number of services in the system shall be "1"

    Examples: 
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |      100 |             0 |                0 |
      | extensions |       50 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | highlights |       90 |            50 |               40 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |

  Scenario Outline: Add a service with invalid parameters
    Given the Owner with username "owner" is logged in
    When "owner" initiates the addition of the service "<name>" with duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then an error message with content "<error>" shall be raised
    Then the service "<name>" shall not exist in the system
    Then the number of services in the system shall be "0"

    Examples: 
      | name       | duration | downtimeStart | downtimeDuration | error                                                       |
      | wash       |        0 |             0 |                0 | Duration must be positive                                   |
      | wash       |       -1 |             0 |                0 | Duration must be positive                                   |
      | extensions |       20 |            10 |                0 | Downtime duration must be positive                          |
      | cut        |       30 |             0 |               -1 | Downtime duration must be 0                                 |
      | cut        |       30 |             0 |               10 | Downtime must not start at the beginning of the service     |
      | cut        |       30 |            -1 |               10 | Downtime must not start before the beginning of the service |
      | highlights |       30 |            20 |               15 | Downtime must not end after the service                     |
      | dry        |       30 |            31 |                1 | Downtime must not start after the end of the service        |

  Scenario Outline: Add an existing service
    Given the following services exist in the system:
      | name | duration | downtimeStart | downtimeDuration |
      | wash |       30 |              0 |                0 |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the addition of the service "<name>" with duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then an error message with content "<error>" shall be raised
    Then the service "<name>" shall still preserve the following properties:
      | name | duration | downtimeStart | downtimeDuration |
      | wash |       30 |              0 |                0 |
    Then the number of services in the system shall be "1"

    Examples: 
      | name | duration | downtimeStart | downtimeDuration | error                       |
      | wash |      100 |             0 |                0 | Service wash already exists |
      | wash |       50 |             0 |                0 | Service wash already exists |
      | wash |       75 |            45 |               30 | Service wash already exists |

  Scenario Outline: Unauthorized attempt to add a service
    Given the following customers exist in the system:
      | username  | password |
      | customer1 |  1234567 |
      | customer2 |  8901234 |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the addition of the service "color" with duration "100", start of down time "0" and down time duration "0"
    Then an error message with content "<error>" shall be raised
    Then the service "color" shall not exist in the system
    Then the number of services in the system shall be "0"

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

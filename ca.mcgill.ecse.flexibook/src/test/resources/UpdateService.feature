Feature: Update Service
  As a business owner, I wish to update my existing services in my business so that I can keep my customers up to date.

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system

  Scenario Outline: Update a service successfully
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | color |       75 |            45 |               30 |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the update of the service "color" to name "<name>", duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then the service "color" shall be updated to name "<name>", duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"

    Examples: 
      | name              | duration | downtimeStart | downtimeDuration |
      | color             |      100 |             0 |                0 |
      | new color         |       50 |            15 |               10 |
      | classical color   |       75 |            45 |               30 |
      | old fashion color |       90 |            50 |               40 |

  Scenario Outline: Update a service with invalid parameters
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | color |       75 |            45 |               30 |
      | dry   |       10 |             0 |                0 |
    Given the Owner with username "owner" is logged in
    When "owner" initiates the update of the service "color" to name "<name>", duration "<duration>", start of down time "<downtimeStart>" and down time duration "<downtimeDuration>"
    Then an error message with content "<error>" shall be raised
    Then the service "color" shall still preserve the following properties:
      | name  | duration | downtimeStart | downtimeDuration |
      | color |       75 |            45 |               30 |

    Examples: 
      | name            | duration | downtimeStart | downtimeDuration | error                                                       |
      | color           |        0 |             0 |                0 | Duration must be positive                                   |
      | color           |      100 |            40 |                0 | Downtime duration must be positive                          |
      | classical color |       75 |             0 |               -1 | Downtime duration must be 0                                 |
      | cut             |       30 |             0 |               10 | Downtime must not start at the beginning of the service     |
      | cut             |       30 |            -1 |               10 | Downtime must not start before the beginning of the service |
      | new color       |       50 |            40 |               15 | Downtime must not end after the service                     |
      | dry             |       90 |            10 |               40 | Service dry already exists                                  |

  Scenario Outline: Unauthorized attempt to update a service
    Given the following customers exist in the system:
      | username  | password |
      | customer1 |  1234567 |
      | customer2 |  8901234 |
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | color |       75 |            45 |               30 |
    Given Customer with username "<username>" is logged in
    When "<username>" initiates the update of the service "color" to name "color", duration "100", start of down time "0" and down time duration "0"
    Then an error message with content "<error>" shall be raised
    Then the service "color" shall still preserve the following properties:
      | name  | duration | downtimeStart | downtimeDuration |
      | color |       75 |            45 |               30 |

    Examples: 
      | username  | error                                            |
      | customer1 | You are not authorized to perform this operation |
      | customer2 | You are not authorized to perform this operation |

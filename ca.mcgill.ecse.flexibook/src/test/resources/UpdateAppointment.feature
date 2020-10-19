Feature: Update appointment
  As a customer, I wish to be able to update my appointment so that I can edit my optional combo items or change my appointment time

  Background: 
    Given a Flexibook system exists
    Given the system's time and date is "2020-12-01+09:00"
    Given an owner account exists in the system
    Given a business exists in the system
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
      | customer3 | 12345678 |
    Given the following services exist in the system:
      | name       | duration | downtimeStart | downtimeDuration |
      | wash       |       10 |             0 |                0 |
      | color      |       75 |            45 |               30 |
      | cut        |       20 |             0 |                0 |
      | dry        |       10 |             0 |                0 |
      | extensions |      100 |             0 |                0 |
    Given the following service combos exist in the system:
      | name         | mainService | services                      | mandatory                   |
      | dye-basic    | color       | wash,color,dry                | false,true,false            |
      | cut-basic    | cut         | wash,cut,dry                  | false,true,false            |
      | super-deluxe | color       | wash,extensions,color,dry,cut | false,false,true,true,false |
    Given the business has the following opening hours
      | day       | startTime | endTime |
      | Monday    | 9:00      | 17:00   |
      | Tuesday   | 9:00      | 17:00   |
      | Wednesday | 9:00      | 17:00   |
      | Thursday  | 9:00      | 17:00   |
      | Friday    | 9:00      | 15:00   |
    Given the business has the following holidays
      | startDate  | endDate    | startTime | endTime |
      | 2020-12-31 | 2021-01-01 | 0:00      | 23:59   |
    Given the following appointments exist in the system:
      | customer  | serviceName | optServices | date       | startTime | endTime |
      | customer3 | cut         | none        | 2020-12-29 | 9:00      | 9:20    |
      | customer2 | cut-basic   | wash,dry    | 2020-12-28 | 13:00     | 13:40   |
      | customer1 | cut         | none        | 2020-12-29 | 12:00     | 12:20   |
      | customer1 | dye-basic   | wash,dry    | 2020-12-28 | 9:00      | 10:35   |

  Scenario Outline: A customer updates his appointment to various time slots
    Given "customer3" is logged in to their account
    When "customer3" attempts to update their "cut" appointment on "2020-12-29" at "9:00" to "<date>" at "<startTime>"
    Then the system shall report that the update was "<result>"
    Then "customer3" shall have a "cut" appointment on "<newDate>" from "<newStartTime>" to "<newEndTime>"
    Then there shall be 0 more appointment in the system

    Examples: 
      # row 1: slot is a holiday
      # row 2: slot is not a business hour (saturday)
      # row 3: slot is occupied by an existing appointment
      # row 4: endTime of slot is not within business hours
      # row 5: regular slot is available
      # row 6: downtime slot is available
      | date       | startTime | result       | newDate    | newStartTime | newEndTime |
      | 2020-12-31 | 10:00     | unsuccessful | 2020-12-29 | 9:00         | 9:20       |
      | 2021-01-02 | 10:00     | unsuccessful | 2020-12-29 | 9:00         | 9:20       |
      | 2020-12-28 | 13:00     | unsuccessful | 2020-12-29 | 9:00         | 9:20       |
      | 2020-12-29 | 16:50     | unsuccessful | 2020-12-29 | 9:00         | 9:20       |
      | 2020-12-29 | 9:10      | successful   | 2020-12-29 | 9:10         | 9:30       |
      | 2020-12-28 | 10:00     | successful   | 2020-12-28 | 10:00        | 10:20      |

  Scenario Outline: A customer updates his service combo appointment to add or remove combo items
    Given "customer3" is logged in to their account
    Given "customer3" has a "super-deluxe" appointment with optional sevices "wash" on "2020-12-29" at "10:00"
    When "customer3" attempts to "<action>" "<comboItem>" from their "super-deluxe" appointment on "2020-12-29" at "10:00"
    Then the system shall report that the update was "<result>"
    Then "customer3" shall have a "super-deluxe" appointment on "2020-12-29" from "10:00" to "<newEndTime>"
    Then there shall be 0 more appointment in the system

    Examples: 
      # row 1: cannot remove main service
      # row 2: cannot remove mandatory service
      # row 3: additional extensions service does not fit in available slot
      # row 4: remove an optional service
      # row 5: additional cut service fits in available slot
      | action | comboItem  | result       | newEndime |
      | remove | color      | unsuccessful | 11:35     |
      | remove | dry        | unsuccessful | 11:35     |
      | add    | extensions | unsuccessful | 11:35     |
      | remove | wash       | successful   | 11:25     |
      | add    | cut        | successful   | 11:55     |

  Scenario Outline: A user attempts to update another user's appointment
    Given "<user>" is logged in to their account
    When "<user>" attempts to update "customer3"'s "cut" appointment on "2020-12-29" at "9:00" to "2020-12-29" at "9:10"
    Then the system shall report "<error>"

    Examples: 
      | user      | error                                                    |
      | owner     | Error: An owner cannot update a customer's appointment   |
      | customer2 | Error: A customer can only update their own appointments |

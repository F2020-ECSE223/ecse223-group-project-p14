Feature: Cancel appointment
  As a customer, I wish to be able to cancel an appointment so that my appointment time slot becomes available for other customers

  Background: 
    Given a Flexibook system exists
    Given the system's time and date is "2020-12-01+09:00"
    Given an owner account exists in the system
    Given a business exists in the system
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
      | customer2 | 12345678 |
    Given the following services exist in the system:
      | name | duration | downtimeStart | downtimeDuration |
      | cut  |       20 |             0 |                0 |
    Given the following appointments exist in the system:
      | customer  | serviceName | date       | startTime | endTime |
      | customer1 | cut         | 2020-12-02 | 9:00      | 9:20    |

  Scenario: A customer attempts to cancel their appointment before the appointment date
    Given "customer1" is logged in to their account
    When "customer1" attempts to cancel their "cut" appointment on "2020-12-02" at "9:00"
    Then "customer1"'s "cut" appointment on "2020-12-02" at "9:00" shall be removed from the system
    Then there shall be 1 less appointment in the system

  Scenario: A customer attempts to cancel their appointment on the appointment date
    Given the system's time and date is "2020-12-02+06:00"
    Given "customer1" is logged in to their account
    When "customer1" attempts to cancel their "cut" appointment on "2020-12-02" at "9:00"
    Then the system shall report "Cannot cancel an appointment on the appointment date"
    Then "customer1" shall have a "cut" appointment on "2020-12-02" from "9:00" to "9:20"
    Then there shall be 0 more appointment in the system

  Scenario Outline: A user attempts to cancel another user's appointment
    Given "<user>" is logged in to their account
    When "<user>" attempts to cancel "customer1"'s "cut" appointment on "2020-12-02" at "9:00"
    Then the system shall report "<error>"
    Then "customer1" shall have a "cut" appointment on "2020-12-02" from "9:00" to "9:20"
    Then there shall be 0 more appointment in the system

    Examples: 
      | user      | error                                             |
      | owner     | An owner cannot cancel an appointment             |
      | customer2 | A customer can only cancel their own appointments |

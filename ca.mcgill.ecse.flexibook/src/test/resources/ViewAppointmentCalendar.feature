Feature: View appointment calendar
  As a user, I want to view the appointment calendar so that I can select a time slot for my appointment and/or browse my scheduled appointments.

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
      | name  | duration | downtimeStart | downtimeDuration |
      | wash  |       10 |             0 |                0 |
      | color |       75 |            45 |               30 |
      | cut   |       20 |             0 |                0 |
      | dry   |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name        | mainService | services       | mandatory        |
      | color-basic | color       | wash,color,dry | false,true,false |
      | cut-basic   | cut         | wash,cut,dry   | false,true,false |
    Given the business has the following opening hours:
      | day       | startTime | endTime |
      | Monday    | 9:00      | 17:00   |
      | Tuesday   | 9:00      | 17:00   |
      | Wednesday | 9:00      | 17:00   |
      | Thursday  | 9:00      | 17:00   |
      | Friday    | 9:00      | 15:00   |
    Given the business has the following holidays:
      | startDate  | endDate    | startTime | endTime |
      | 2020-12-31 | 2021-01-01 | 0:00      | 23:59   |
    Given the following appointments exist in the system:
      | customer  | serviceName | optServices | date       | startTime | endTime |
      | customer1 | color-basic | wash,dry    | 2020-12-28 | 9:00      | 10:35   |
      | customer2 | cut-basic   | wash,dry    | 2020-12-28 | 13:00     | 13:40   |
      | customer3 | cut-basic   | wash,dry    | 2020-12-29 | 9:00      | 9:40    |

  Scenario Outline: A user requests the appointment calendar for a specified week
    Given "<user>" is logged in to their account
    When "<user>" requests the appointment calendar for the week starting on "2020-12-27"
    Then the following slots shall be unavailable:
      # row 1,2: color-basic appointment has a downtime
      | date       | startTime | endTime |
      | 2020-12-28 | 9:00      | 9:55    |
      | 2020-12-28 | 10:25     | 10:35   |
      | 2020-12-28 | 13:00     | 13:40   |
      | 2020-12-29 | 9:00      | 9:40    |
      | 2020-12-31 | 9:00      | 17:00   |
      | 2021-01-01 | 9:00      | 15:00   |
    Then the following slots shall be available:
      # row 1: available slot during downtime of color-basic
      | date       | startTime | endTime |
      | 2020-12-28 | 9:55      | 10:25   |
      | 2020-12-28 | 10:35     | 13:00   |
      | 2020-12-28 | 13:40     | 17:00   |
      | 2020-12-29 | 9:40      | 17:00   |
      | 2020-12-30 | 9:00      | 17:00   |

    Examples: 
      | user      |
      | customer1 |
      | owner     |

  Scenario Outline: A user requests the appointment calendar for a specified day
    Given "<user>" is logged in to their account
    When "<user>" requests the appointment calendar for the day of "2020-12-28"
    Then the following slots shall be unavailable:
      # row 1,2: color-basic appointment has a downtime
      | date       | startTime | endTime |
      | 2020-12-28 | 9:00      | 9:55    |
      | 2020-12-28 | 10:25     | 10:35   |
      | 2020-12-28 | 13:00     | 13:40   |
    Then the following slots shall be available:
      # row 1: available slot during downtime of color-basic
      | date       | startTime | endTime |
      | 2020-12-28 | 9:55      | 10:25   |
      | 2020-12-28 | 10:35     | 13:00   |
      | 2020-12-28 | 13:40     | 17:00   |

    Examples: 
      | user      |
      | customer1 |
      | owner     |

  Scenario Outline: A user requests the appointment calendar for an invalid day
    Given "<user>" is logged in to their account
    When "<user>" requests the appointment calendar for the day of "<date>"
    Then the system shall report "<date> is not a valid date"

    Examples: 
      # row 1,3: invalid month
      # row 2,4: invalid day
      | user      | date       |
      | customer1 | 2020-16-20 |
      | customer1 | 2020-12-32 |
      | owner     | 2020-16-20 |
      | owner     | 2020-12-32 |

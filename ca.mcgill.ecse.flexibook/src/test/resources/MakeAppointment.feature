Feature: Make appointment
  As a customer, I wish to be able to make an appointment so that I can schedule a service

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
      | name      | mainService | services       | mandatory        |
      | dye-basic | color       | wash,color,dry | false,true,false |
      | cut-basic | cut         | wash,cut,dry   | false,true,false |
    Given the business has the following opening hours
      | day       | startTime | endTime |
      | Monday    | 09:00      | 17:00   |
      | Tuesday   | 09:00      | 17:00   |
      | Wednesday | 09:00      | 17:00   |
      | Thursday  | 09:00      | 17:00   |
      | Friday    | 09:00      | 15:00   |
    Given the business has the following holidays
      | startDate  | endDate    | startTime | endTime |
      | 2020-12-31 | 2021-01-01 | 00:00      | 23:59   |
    Given the following appointments exist in the system:
      | customer  | serviceName | optServices | date       | startTime | endTime |
      | customer1 | dye-basic   | wash,dry    | 2020-12-28 | 09:00      | 10:35   |
      | customer2 | cut-basic   | wash,dry    | 2020-12-28 | 13:00     | 13:40   |
      | customer3 | cut-basic   | wash,dry    | 2020-12-29 | 09:00      | 09:40    |

  Scenario Outline: A customer attempts to make various valid appointments for services
    Given "customer1" is logged in to their account
    When "customer1" schedules an appointment on "<date>" for "<serviceName>" at "<startTime>"
    Then "customer1" shall have a "<serviceName>" appointment on "<date>" from "<startTime>" to "<endTime>"
    Then there shall be 1 more appointment in the system

    Examples: 
      # row 1: appointment in regular available slot
      # row 2: appointment during downtime of dye-basic
      | serviceName | date       | startTime | endTime |
      | cut         | 2020-12-29 | 09:40      | 10:00   |
      | cut         | 2020-12-28 | 10:00     | 10:20   |

  Scenario Outline: A customer attempts to make various valid appointments for service combos
    Given "customer1" is logged in to their account
    When "customer1" schedules an appointment on "<date>" for "<serviceName>" with "<optionalServices>" at "<startTime>"
    Then "customer1" shall have a "<serviceName>" appointment on "<date>" from "<startTime>" to "<endTime>"
    Then there shall be 1 more appointment in the system

    Examples: 
      # row 1: appointment in regular available slot
      # row 2: appointment during downtime of dye-basic
      | serviceName | optionalServices | date       | startTime | endTime |
      | cut-basic   | wash,dry         | 2020-12-30 | 10:00     | 10:40   |
      | cut-basic   | dry              | 2020-12-28 | 09:55      | 10:25   |

  Scenario Outline: A customer attempts to make various invalid appointments for services
    Given "customer1" is logged in to their account
    When "customer1" schedules an appointment on "<date>" for "<serviceName>" at "<startTime>"
    Then the system shall report "There are no available slots for <serviceName> on <date> at <startTime>"
    Then there shall be 0 more appointment in the system

    Examples: 
      # row 1: slot is occupied by existing appointment
      # row 2: slot corresponds to down time that is not long enough
      # row 3: slot is during holiday
      # row 4: slot is not during a business hour (saturday)
      # row 5: slot is in 2019
      | serviceName | date       | startTime |
      | cut         | 2020-12-29 | 09:00      |
      | color       | 2020-12-28 | 10:00     |
      | cut         | 2020-12-31 | 09:00      |
      | cut         | 2021-01-02 | 09:00      |
      | cut         | 2019-12-31 | 09:00      |

  Scenario Outline: A customer attempts to make various invalid appointments for service combos
    Given "customer1" is logged in to their account
    When "customer1" schedules an appointment on "<date>" for "<serviceName>" with "<optionalServices>" at "<startTime>"
    Then the system shall report "There are no available slots for <serviceName> on <date> at <startTime>"
    Then there shall be 0 more appointment in the system

    Examples: 
      # row 1: slot is occupied by existing appointment
      # row 2: slot corresponds to down time that is not long enough
      # row 3: slot is during holiday
      # row 4: slot is not during a business hour (saturday)
      # row 5: slot is in 2019
      | serviceName | optionalServices | date       | startTime |
      | cut-basic   | wash,dry         | 2020-12-29 | 09:00      |
      | dye-basic   | wash,dry         | 2020-12-28 | 10:00     |
      | cut-basic   | dry              | 2020-12-31 | 09:00      |
      | cut-basic   | dry              | 2021-01-02 | 09:00      |
      | cut-basic   | dry              | 2019-01-02 | 09:00      |

  Scenario: The owner attempts to make an appointment
    Given "owner" is logged in to their account
    When "owner" schedules an appointment on "2020-12-29" for "cut" at "09:40"
    Then the system shall report "An owner cannot make an appointment"
    Then there shall be 0 more appointment in the system

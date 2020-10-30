Feature: Appointment management process

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system
    Given a business exists in the system
    Given the following customers exist in the system:
      | username  | password |
      | customer1 | 12345678 |
    Given the business has the following opening hours
      | day       | startTime | endTime |
      | Monday    | 8:00      | 18:00   |
      | Tuesday   | 8:00      | 18:00   |
      | Wednesday | 8:00      | 18:00   |
      | Thursday  | 8:00      | 18:00   |
      | Friday    | 8:00      | 18:00   |
    Given the business has the following holidays
      | startDate  | endDate    | startTime | endTime |
      | 2020-12-18 | 2021-12-18 | 10:00      | 23:59   |
    Given a "vacation" time slot exists with start time "2020-12-14" at "12:00" and end time "2020-12-16" at "13:00"
    Given "customer1" has 0 no-show records
    Given the following services exist in the system:
      | name  | duration | downtimeStart | downtimeDuration |
      | wash  |       10 |             0 |                0 |
      | color |       75 |            45 |               30 |
      | cut   |       20 |             0 |                0 |
      | dry   |       10 |             0 |                0 |
    Given the following service combos exist in the system:
      | name         | mainService | services           | mandatory             |
      | color-deluxe | color       | wash,color,dry,cut | false,true,true,false |
    Given the following appointments exist in the system:
      | customer  | serviceName  | selectedComboItems | date       | startTime | endTime |
      | customer1 | color-deluxe | wash,color,cut,dry | 2020-12-08 | 13:00     | 14:55   |

# the When statements include when the action is executed at the end
# e.g., the statement
#    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
# refers to the action that user issues the "make an appointment for ..." command on 2020-12-04 at 09:00
# this allows setting the system time to this specified date and enables thorough testing the behavior of the software

  Scenario: Change the appointment for a service at least one day ahead
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to change the service in the appointment to "wash" at "2020-12-09+09:00"
    Then the appointment shall be booked
    Then the service in the appointment shall be "wash"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:10"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the appointment for a service on its day
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to change the service in the appointment to "wash" at "2020-12-10+09:00"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change date and time of appointment for a service at least one day ahead
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-11" and time to "11:00" at "2020-12-09+09:30"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-11" with start time "11:00" and end time "11:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the date and time of appointment for a service on its day
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-11" and time to "11:00" at "2020-12-10+09:30"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Cancel the appointment for a service at least one day ahead
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to cancel the appointment at "2020-12-09+09:00"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 1 appointment

  Scenario: Cancel the appointment for a service on its day
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to cancel the appointment at "2020-12-10+09:00"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the appointment for a service combo at least one day ahead
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to add the optional service "cut" to the service combo in the appointment at "2020-12-09+09:00"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry,cut" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:45"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the appointment for a service combo on its day
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to add the optional service "cut" to the service combo in the appointment at "2020-12-10+09:00"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the date and time of appointment for a service combo at least one day ahead
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-11" and time to "11:00" at "2020-12-09+09:30"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-11" with start time "11:00" and end time "12:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Change the date and time of appointment for a service combo on its day
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-11" and time to "11:00" at "2020-12-10+09:30"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer attempts to cancel the appointment for a service combo at least one day ahead
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to cancel the appointment at "2020-12-09+09:00"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 1 appointment

  Scenario: Customer attempts to cancel the appointment for a service combo on its day
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to cancel the appointment at "2020-12-10+09:00"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives and the appointment for service completes without any changes
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When the owner ends the appointment at "2020-12-10+10:20"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 1 appointment

  Scenario: Customer arrives and the appointment for service combo completes without any changes
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When the owner ends the appointment at "2020-12-10+11:25"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 1 appointment

  Scenario: Customer arrives to the appointment and updates the service combo
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-10+10:05"
    Then the appointment shall be in progress
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "wash,color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:35"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives to the appointment early and the owner attempts to start the appointment
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+09:55"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer does not show up for the appointment
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner attempts to register a no-show for the appointment at "2020-12-10+11:25"
    Then the user "customer1" shall have 1 no-show records
    Then the system shall have 1 appointment

  Scenario: Change date of appointment with service while the appointment is in progress
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When "customer1" attempts to update the date to "2020-12-11" and time to "11:00" at "2020-12-10+10:05"
    Then the appointment shall be in progress
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Cancel appointment while the appointment is in progress
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When "customer1" attempts to cancel the appointment at "2020-12-10+10:05"
    Then the appointment shall be in progress
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Start appointment while the appointment is in progress
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:00"
    When the owner starts the appointment at "2020-12-10+10:05"
    Then the appointment shall be in progress
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Register no-show for customer while the appointment is in progress
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-10+10:01"
    When the owner attempts to register a no-show for the appointment at "2020-12-10+10:10"
    Then the appointment shall be in progress
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: End appointment while the appointment is not in progress
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When the owner attempts to end the appointment at "2020-12-10+09:10"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service and new time overlaps with another appointment
    When "customer1" makes a "wash" appointment for the date "2020-12-08" and time "12:45" at "2020-12-04+09:00"
    When "customer1" attempts to change the service in the appointment to "cut" at "2020-12-07+09:00"
    Then the appointment shall be booked
    Then the service in the appointment shall be "wash"
    Then the appointment shall be for the date "2020-12-08" with start time "12:45" and end time "12:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service and new time is outside business hours
    When "customer1" makes a "wash" appointment for the date "2020-12-08" and time "17:45" at "2020-12-04+09:00"
    When "customer1" attempts to change the service in the appointment to "cut" at "2020-12-07+09:00"
    Then the appointment shall be booked
    Then the service in the appointment shall be "wash"
    Then the appointment shall be for the date "2020-12-08" with start time "17:45" and end time "17:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service and new time overlaps vacation
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-16" and time to "12:00" at "2020-12-07+09:30"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service and new date is on a holiday
    When "customer1" makes a "cut" appointment for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-18" and time to "12:00" at "2020-12-07+09:30"
    Then the appointment shall be booked
    Then the service in the appointment shall be "cut"
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "10:20"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service combo and new time overlaps with another appointment
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-08" and time "11:35" at "2020-12-04+09:00"
    When "customer1" attempts to add the optional service "cut" to the service combo in the appointment at "2020-12-07+09:00"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-08" with start time "11:35" and end time "13:00"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service combo and new time is outside business hours
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-08" and time "16:35" at "2020-12-04+09:00"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-07+09:00"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-08" with start time "16:35" and end time "18:00"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service combo and new time overlaps vacation
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-16" and time to "12:00" at "2020-12-07+09:30"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Update appointment time for service combo and new date is on a holiday
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-10" and time "10:00" at "2020-12-04+09:00"
    When "customer1" attempts to update the date to "2020-12-18" and time to "12:00" at "2020-12-07+09:30"
    Then the appointment shall be booked
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-10" with start time "10:00" and end time "11:25"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives to the appointment and adds an optional service to the service combo and new time overlaps an existing appointment
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-08" and time "11:30" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-08+11:30"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-08+11:35"
    Then the appointment shall be in progress
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-08" with start time "11:30" and end time "12:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives to the appointment and adds an optional service to the service combo and new time is outside business hours
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-08" and time "16:30" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-08+16:32"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-08+16:33"
    Then the appointment shall be in progress
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-08" with start time "16:30" and end time "17:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives to the appointment and adds an optional service to the service combo and new time overlaps vacation
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-14" and time "10:30" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-14+10:30"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-14+10:34"
    Then the appointment shall be in progress
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-14" with start time "10:30" and end time "11:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

  Scenario: Customer arrives to the appointment and adds an optional service to the service combo and new time overlaps holiday
    When "customer1" makes a "color-deluxe" appointment without choosing optional services for the date "2020-12-18" and time "08:30" at "2020-12-04+09:00"
    When the owner starts the appointment at "2020-12-18+08:30"
    When "customer1" attempts to add the optional service "wash" to the service combo in the appointment at "2020-12-18+08:34"
    Then the appointment shall be in progress
    Then the service combo in the appointment shall be "color-deluxe"
    Then the service combo shall have "color,dry" selected services
    Then the appointment shall be for the date "2020-12-18" with start time "08:30" and end time "09:55"
    Then the username associated with the appointment shall be "customer1"
    Then the user "customer1" shall have 0 no-show records
    Then the system shall have 2 appointments

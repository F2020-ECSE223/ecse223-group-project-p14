Feature: Sign up for customer account
  As a prospective customer, I want to create an account with username and password
  so that I can log in later

  Background: 
    Given a Flexibook system exists

  Scenario: Create a new account successfully
    Given there is no existing username "User1"
    When the user provides a new username "User1" and a password "password1"
    Then a new customer account shall be created
    Then the account shall have username "User1" and password "password1"

  Scenario Outline: Create a new account with incomplete form
    When the user provides a new username "<username>" and a password "<password>"
    Then no new account shall be created
    Then an error message "<error>" shall be raised

    Examples: 
      | username | password | error                         |
      |          | apple    | The user name cannot be empty |
      | User1    |          | The password cannot be empty  |

  Scenario: Create a new user with a username that already exists
    Given there is an existing username "User1"
    When the user provides a new username "User1" and a password "password1"
    Then no new account shall be created
    Then an error message "The username already exists" shall be raised

  Scenario: Create a customer account when logged in as the owner
    Given there is an existing username "owner"
    Given the user is logged in to an account with username "owner"
    When the user provides a new username "User1" and a password "password1"
    Then no new account shall be created
    Then an error message "You must log out of the owner account before creating a customer account" shall be raised

Feature: Login as customer or owner
  As an owner, I want to log in so that I can access the space to manage my business. 
  As a customer, I want to log in so that I can manage my appointments.
  The owner account is created automatically if it does not exist.

  Background: 
    Given a Flexibook system exists
    Given the following customers exist in the system:
      | username | password |
      | User1    | apple    |
      | User2    | grape    |

  Scenario: Log in successfully
    When the user tries to log in with username "User1" and password "apple"
    Then the user should be successfully logged in

  Scenario: Log in with a username that does not exist
    When the user tries to log in with username "User3" and password "apple"
    Then the user should not be logged in
    Then an error message "Username/password not found" shall be raised

  Scenario: Log in as the owner for the first time
    When the user tries to log in with username "owner" and password "owner"
    Then a new account shall be created
    Then the account shall have username "owner" and password "owner"
    Then the user shall be successfully logged in

  Scenario: Log in with incorrect possword
    When the user tries to log in with username "User1" and password "grape"
    Then the user should not be logged in
    Then an error message "Username/password not found" shall be raised

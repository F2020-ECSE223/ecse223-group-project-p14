Feature: Logout
  As a user, I want to log out of the application so that the next user does not have access to my information

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system with username "owner" and password "ownerPass"
    Given the following customers exist in the system:
      | username | password |
      | User1    | apple    |
      | User2    | grape    |

  Scenario: Log out with a user that is logged out
    Given the user is logged out
    When the user tries to log out
    Then an error message "The user is already logged out" shall be raised

  Scenario: Log out with a user that is logged in
    Given the user is logged in to an account with username "User1"
    When the user tries to log out
    Then the user shall be logged out

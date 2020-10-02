Feature: Delete customer account
  As a user, I want to delete my own account so that 
  the personal information is deleted from the system

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system with username "owner" and password "ownerPass"
    Given the following customers exist in the system:
      | username | password |
      | User1    | apple    |
      | User2    | grape    |
    Given the account with username "User1" has pending appointments

  Scenario Outline: Delete customer account successfully
    Given the user is logged in to an account with username "<username>"
    When the user tries to delete account with the username "<target>"
    Then the account with the username "<target>" does not exist
    Then all associated appointments of the account with the username "<username>" shall not exist
    Then the user shall be logged out

    Examples: 
      | username | target |
      | User1    | User1  |

  Scenario Outline: Delete account without permission
    Given the user is logged in to an account with username "<username>"
    When the user tries to delete account with the username "<target>"
    Then the account with the username "<target>" exists
    Then an error message "You do not have permission to delete this account" shall be raised

    Examples: 
      | username | target |
      | owner    | owner  |
      | User1    | User2  |
      | owner    | User2  |
      | User1    | owner  |

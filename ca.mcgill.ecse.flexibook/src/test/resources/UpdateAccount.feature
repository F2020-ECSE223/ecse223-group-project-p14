Feature: Update customer or owner account
  As a user, I want to be update my username and password so that I can login later with the 
  new information

  Background: 
    Given a Flexibook system exists
    Given an owner account exists in the system with username "owner" and password "ownerPass"
    Given the following customers exist in the system:
      | username | password |
      | User1    | apple    |
      | User2    | grape    |

  Scenario Outline: Update account successfully
    Given the user is logged in to an account with username "<username>"
    When the user tries to update account with a new username "<newUser>" and password "<newPassword>"
    Then the account shall have username "<newUser>" and password "<newPassword>"

    Examples: 
      | username | newUser | newPassword |
      | User1    | User3   | watermelon  |
      | owner    | owner   | dragonfruit |

  Scenario Outline: Update account failed
    Given the user is logged in to an account with username "<username>"
    When the user tries to update account with a new username "<newUser>" and password "<newPassword>"
    Then the account shall have username "<username>" and password "<oldPassword>"
    Then an error message "<error>" shall be raised

    Examples: 
      | username | newUser | newPassword | oldPassword | error                                     |
      | User1    | User2   | watermelon  | apple       | Username not available                    |
      | owner    | User1   | dragonfruit | ownerPass   | Changing username of owner is not allowed |

  Scenario Outline: Update account by the account holder with incomplete form
    Given the user is logged in to an account with username "User1"
    When the user tries to update account with a new username "<username>" and password "<password>"
    Then the account shall not be updated
    Then an error message "<error>" shall be raised

    Examples: 
      | username | password | error                         |
      |          | apple    | The user name cannot be empty |
      | User1    |          | The password cannot be empty  |

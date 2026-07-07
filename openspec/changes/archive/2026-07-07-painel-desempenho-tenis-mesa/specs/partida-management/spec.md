## ADDED Requirements

### Requirement: User can create a match with dynamic sets
The system SHALL allow creating a match within a championship with adversario, optional fase, data, dynamic sets (min 1), and optional linkYoutube.

#### Scenario: Create match with valid data
- **WHEN** user submits the match form with at least 1 valid set, valid data, adversario filled, and optional linkYoutube matching allowed patterns
- **THEN** system saves the match, calculates the result, and redirects to the championship detail page with success flash message

#### Scenario: Create match with invalid set score
- **WHEN** user submits a set where winner has less than 11 points or advantage less than 2
- **THEN** system returns the form with specific set validation error, preserving all entered data

#### Scenario: Create match with tied total sets
- **WHEN** user submits a match with equal number of sets won by athlete and opponent
- **THEN** system returns the form with error message indicating the tie must be resolved, preserving all entered data

#### Scenario: Add and remove set rows via JavaScript
- **WHEN** user clicks "Adicionar set" on the match form
- **THEN** system SHALL provide a new set input row via JavaScript; when user clicks "Remover" on a set row, that row is removed; at least 1 set must remain

### Requirement: Match result is calculated from sets
The system SHALL calculate VITORIA or DERROTA based on set scores, never persisted in the database.

#### Scenario: Victory calculated correctly
- **WHEN** athlete wins more sets than opponent
- **THEN** match result is VITORIA

#### Scenario: Defeat calculated correctly
- **WHEN** athlete wins fewer sets than opponent
- **THEN** match result is DERROTA

### Requirement: User can edit a match
The system SHALL allow editing a match's data and its sets, with result recalculated on save.

#### Scenario: Edit match
- **WHEN** user submits the edit form with modified match or set data
- **THEN** system updates the match, recalculates result, and redirects to championship detail

### Requirement: User can delete a match
The system SHALL allow deleting a match after confirmation.

#### Scenario: Delete match
- **WHEN** user confirms deletion of a match
- **THEN** system deletes the match and redirects to championship detail with success flash message

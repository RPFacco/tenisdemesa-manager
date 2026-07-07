## ADDED Requirements

### Requirement: User can create a championship
The system SHALL allow the user to create a championship with nome, local, dataInicio, dataFim, and optional categoria.

#### Scenario: Create championship with valid data
- **WHEN** user submits the championship form with all required fields filled and dataFim >= dataInicio
- **THEN** system saves the championship and redirects to `/campeonatos` with success flash message

#### Scenario: Create championship with invalid data
- **WHEN** user submits the championship form with missing required fields or dataFim < dataInicio
- **THEN** system returns the form with validation errors and previously entered data preserved

### Requirement: User can list championships
The system SHALL display a list of all championships with nome, local, period, and medal count.

#### Scenario: View championship list
- **WHEN** user navigates to `/campeonatos`
- **THEN** system displays all championships ordered by dataInicio descending, each showing name, location, date range, and medal counts

### Requirement: User can view championship details
The system SHALL display championship details including statistics (wins, losses, win rate), matches list, and medals list.

#### Scenario: View championship detail
- **WHEN** user clicks a championship in the list
- **THEN** system displays `/campeonatos/{id}` with championship info, per-campeonato statistics, partidas ordered by data, and medalhas grouped by tipo

### Requirement: User can edit a championship
The system SHALL allow editing an existing championship's data.

#### Scenario: Edit championship successfully
- **WHEN** user submits the edit form with valid data
- **THEN** system updates the championship and redirects to its detail page with success flash message

### Requirement: User can delete a championship
The system SHALL allow deleting a championship with cascade deletion of all associated partidas and medalhas after confirmation.

#### Scenario: Delete championship
- **WHEN** user confirms deletion of a championship
- **THEN** system deletes the championship, its partidas, and its medalhas, then redirects to `/campeonatos` with success flash message

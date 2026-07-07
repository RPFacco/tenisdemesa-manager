## ADDED Requirements

### Requirement: User can create a training session
The system SHALL allow creating a training session with data, duracaoHoras (> 0), and tipo (TECNICA, FISICO, JOGO).

#### Scenario: Create training with valid data
- **WHEN** user submits the training form with valid data, non-future date, duracaoHoras > 0, and tipo selected
- **THEN** system saves the training and redirects to `/treinos` with success flash message

#### Scenario: Create training with future date
- **WHEN** user submits the training form with a future date
- **THEN** system returns the form with date validation error

### Requirement: User can list training sessions with summary
The system SHALL display a list of all training sessions with summary cards showing total hours today, this week, and this month, with breakdown by tipo.

#### Scenario: View training list with summaries
- **WHEN** user navigates to `/treinos`
- **THEN** system displays summary cards (hours today/week/month with tipo breakdown) followed by the full list of training sessions ordered by data descending

### Requirement: User can delete a training session
The system SHALL allow deleting a training session after confirmation.

#### Scenario: Delete training
- **WHEN** user confirms deletion of a training session
- **THEN** system deletes the training and redirects to `/treinos` with success flash message

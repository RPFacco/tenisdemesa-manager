## ADDED Requirements

### Requirement: User can add a medal to a championship
The system SHALL allow adding medals to a championship with TipoMedalha (OURO, PRATA, BRONZE) and Modalidade (SIMPLES, DUPLAS, DUPLAS_MISTAS, EQUIPE). No limit on medals per championship.

#### Scenario: Add medal successfully
- **WHEN** user submits the medal form with tipo and modalidade
- **THEN** system saves the medal and redirects to championship detail with success flash message

#### Scenario: Add medal with missing fields
- **WHEN** user submits the medal form without selecting tipo or modalidade
- **THEN** system returns the form with validation errors

### Requirement: User can delete a medal
The system SHALL allow deleting a medal after confirmation.

#### Scenario: Delete medal
- **WHEN** user confirms deletion of a medal
- **THEN** system deletes the medal and redirects to championship detail with success flash message

## ADDED Requirements

### Requirement: User can view overall statistics
The system SHALL display aggregated statistics across all championships on the Geral page.

#### Scenario: View geral page with data
- **WHEN** user navigates to `/geral`
- **THEN** system displays total wins, total losses, win rate percentage (rounded to integer), total medals by tipo (ouro/prata/bronze), total championships

#### Scenario: View geral page with no matches
- **WHEN** user navigates to `/geral` and there are no matches registered
- **THEN** system displays win rate as "—" and streaks as "—"

### Requirement: User can view current streak
The system SHALL calculate and display the current streak of consecutive same results (wins or losses) from the most recent match backwards.

#### Scenario: Current streak displayed
- **WHEN** user navigates to `/geral`
- **THEN** system calculates streak from most recent match backwards and displays as "4V" (4 consecutive wins) or "2D" (2 consecutive losses) or "—" (no matches)

### Requirement: Training summary by period
The system SHALL display training summary aggregated by day, week (ISO Monday-Sunday), and month with breakdown by tipo.

#### Scenario: Training summary on training list
- **WHEN** user navigates to `/treinos`
- **THEN** system displays total duracaoHoras for today, current week, and current month, each with breakdown by tipo (tecnica, fisico, jogo)

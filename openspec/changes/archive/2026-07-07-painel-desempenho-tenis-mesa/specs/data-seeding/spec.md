## ADDED Requirements

### Requirement: System seeds sample data on first run
The system SHALL populate the database with sample data on first execution if no championships exist.

#### Scenario: Seed executes on empty database
- **WHEN** application starts and `campeonatoRepository.count() == 0`
- **THEN** system creates 2 sample championships, 3-4 matches per championship with varied sets (wins and losses, some with YouTube links), 1-2 medals per championship, and 8-10 training records distributed across the last 3-4 weeks with all 3 tipos

#### Scenario: Seed does not execute on non-empty database
- **WHEN** application starts and `campeonatoRepository.count() > 0`
- **THEN** system skips seeding entirely

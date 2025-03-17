@trip
Feature: This feature files contains BDD scenarios to test trip
  service endpoints - GET trips, POST trip and PATCH trip.

  Scenario: Get trip details with valid trip Id.
    Given user had booked a trip with id "a12c34e5-678d-90ef-1234-56789abcdefg"
    When check that API status code is 200
    Then validate the response

  Scenario: Get trip details with invalid trip Id. Validate 404 status code
    Given user had booked a trip with id "x1231y"
    When check that API status code is 404

  Scenario: Make sure that trip Id cannot be empty
    Given user had booked a trip with id ""
    When check that API status code is 400

  Scenario: Create a new trip from `berlin hbf` to `Berlin Alexanderplatz` and validate response.
    Given user wants to book a trip
      | origin              | destination           | customerId                           | service_class_id |
      | Berlin Hauptbahnhof | Berlin Alexanderplatz | c5447e5a-94b5-4513-8688-4cf0be213879 | 1                |
    When check that API status code is 201
    Then validate the trips API response

  Scenario: Create a invalid trip. Pass wrong customer Id and service class Id.
    Given user wants to book a trip
      | origin         | destination | customerId | service_class_id |
      | Hamburg altona | Hamburg hbf | 1234       | -2               |
    And check that API status code is 400

  Scenario: Creating a trip without required fields should throw error.
    Given user wants to book a trip
      | service_class_id |
      | 1                |
    When check that API status code is 400

  Scenario: Update the state of existing trip.
    Given user had booked a trip with id "8d78e032-2de1-4887-a2c5-96e0e093cc61"
    When check that current state of trip "8d78e032-2de1-4887-a2c5-96e0e093cc61" is "ORDERED"
    And update the trip "8d78e032-2de1-4887-a2c5-96e0e093cc61"
      | state    |
      | ACCEPTED |
    Then check that current state of trip "8d78e032-2de1-4887-a2c5-96e0e093cc61" is "ACCEPTED"

  Scenario: Create a new trip and cancel it. Validate the state change.
    Given user wants to book a trip
      | origin              | destination           | customerId                           | service_class_id |
      | Berlin Hauptbahnhof | Berlin Alexanderplatz | c87323c-94b5-4513-8688-5d7672bab8129 | 1                |
    When check that current state of trip "c87323c-94b5-4513-8688-5d7672bab8129" is "ORDERED"
    And update the trip "c87323c-94b5-4513-8688-5d7672bab8129"
      | state     |
      | CANCELLED |
    Then check that current state of trip "c87323c-94b5-4513-8688-5d7672bab8129" is "CANCELLED"

  Scenario: Invalid state transition
    Given user had booked a trip with id "73d2b3c4-df94-4855-afea-ad8bc6f1a5b0"
    When check that current state of trip "73d2b3c4-df94-4855-afea-ad8bc6f1a5b0" is "DROPPED_OFF"
    And update the trip "c87323c-94b5-4513-8688-5d7672bab8129"
      | state    |
      | ACCEPTED |
    When check that API status code is 404

  Scenario: Update trip to unknown state and validate 404 status code
    Given user had booked a trip with id "2328d78e-4887-2de1-cc61-96e0e093a2c5"
    When check that current state of trip "2328d78e-4887-2de1-cc61-96e0e093a2c5" is "ORDERED"
    And update the trip "2328d78e-4887-2de1-cc61-96e0e093a2c5"
      | state   |
      | SUCCESS |
    When check that API status code is 404
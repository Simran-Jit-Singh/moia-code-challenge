@query
Feature: This feature file contains query scripts to monitor user data.

  @tripCount
  Scenario: Number of trips ordered by a customer
    Given fetch the trips details
    And Get the trip count for user "c56d78e9-012f-3456-789a-bcdef012g34h"

  @activeTrip
  Scenario: Number of active trips
    Given fetch the trips details
    And no of trips active at 1729087200

  @dropOffStop
  Scenario: Most popular drop off station
    Given fetch the trips details
    And most popular drop off stop

  @totalDuration
  Scenario: Total duration of active trips
    Given fetch the trips details
    And get the total duration of active trips


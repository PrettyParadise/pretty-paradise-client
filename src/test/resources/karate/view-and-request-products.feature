Feature: view and request products
  Scenario: can view and request products
    Given request 'http://localhost:8002/prettyparadise'
    When method get
    Then status 200
    And match response == { products: [{ name: "Glitteratti", price: 44.50,  }, { name: "Vera", price: 34.50 }] }

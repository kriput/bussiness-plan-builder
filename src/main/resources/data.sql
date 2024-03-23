INSERT INTO financial_forecast (name, selling_in_credit_rate, building_deprecation_rate, equipment_deprecation_rate)
VALUES ('Joogid', 2, 5, 5);
INSERT INTO product (financial_forecast_id, unit, name, tax, stock_reserve_rate)
VALUES (1, 'TK', 'limonaad', 0.22, 0.1);
INSERT INTO product_per_period (product_id, quantity, for_export, price, cost_per_item, year)
VALUES (1, 1000, 0.1, 1.0, 0.50, 2024);

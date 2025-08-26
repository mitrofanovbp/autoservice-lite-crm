-- === Customers
INSERT INTO customers (full_name, phone, email, created_at, updated_at) VALUES
('Ivan Ivanov',  '+77001234567', 'ivan@example.com',  NOW(), NOW()),
('Petr Petrov',  '+77005553322', 'petr@example.com',  NOW(), NOW()),
('Anna Smirnova','+77007778899', 'anna@example.com',  NOW(), NOW());

-- === Vehicles
INSERT INTO vehicles (vin, make, model, production_year, owner_id, created_at, updated_at) VALUES
('VINTOYOTA001', 'Toyota', 'Camry', 2017, (SELECT id FROM customers WHERE email='ivan@example.com'), NOW(), NOW()),
('VINHONDA002',  'Honda',  'Civic', 2019, (SELECT id FROM customers WHERE email='petr@example.com'), NOW(), NOW()),
('VINVW003',     'Volkswagen', 'Passat', 2015, (SELECT id FROM customers WHERE email='anna@example.com'), NOW(), NOW());

-- === Work Orders
INSERT INTO work_orders (vehicle_id, status, problem_description, assigned_to, total_cost, created_at, updated_at) VALUES
((SELECT id FROM vehicles WHERE vin='VINTOYOTA001'), 'OPEN',        'The Check Engine is ON', 'Master A', 15000.00, NOW(), NOW()),
((SELECT id FROM vehicles WHERE vin='VINHONDA002'),  'IN_PROGRESS', 'Replacement of brake pads', 'Master B', 38000.00, NOW(), NOW()),
((SELECT id FROM vehicles WHERE vin='VINVW003'),     'DONE',        'Routine Maintenance', 'Master C', 25000.00, NOW(), NOW()),
((SELECT id FROM vehicles WHERE vin='VINTOYOTA001'), 'CANCELLED',   'Suspension diagnostics', NULL, NULL, NOW(), NOW());

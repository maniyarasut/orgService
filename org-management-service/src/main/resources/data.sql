  
  INSERT INTO ORG (name, location, email) VALUES
  ('OrgA', 'Chennai', 'orga@gmail.com'),
  ('OrgB', 'Bangalore', 'orgb@marvel.com');
  
INSERT INTO EMP (first_name, last_name, email,orgId) VALUES
  ('Lokesh', 'Gupta', 'abc@gmail.com',1),
  ('Deja', 'Vu', 'xyz@email.com',1),
  ('Caption', 'America', 'cap@marvel.com',2);

  
    
  INSERT INTO Asset (name, orgId, empId) VALUES
  ('LAPTOP',1, 1),
  ('PHONE',1, 2),
  ('LAPTOP',2, 3);
  
  
  INSERT INTO login (email,password,role,isactive) VALUES
  ('user','$2a$12$6Df0r5p2WfLwN6.S.fbL1.kRlJU2/a6TSqnx/4Faa6xDpPJqTMj1C','user','Y'),
  ('admin','$2a$12$Inxd1JxrnsN7HO8I2ZFr9uUXWtWkkTHlAA1tydpoJVEaf97h5D6G2','admin','Y');
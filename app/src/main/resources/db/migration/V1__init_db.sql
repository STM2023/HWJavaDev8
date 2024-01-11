CREATE TABLE IF NOT EXISTS  worker (
               id INT PRIMARY KEY,
               name VARCHAR(1000)    NOT  NULL  CHECK ( LENGTH (name)>=2 ),
               birthday DATE  CHECK ( YEAR(birthday) >  1900 ),
               level   VARCHAR(7)   NOT   NULL   CHECK ( level  IN  ('Trainee', 'Junior', 'Middle', 'Senior')),
               salary INT NOT  NULL  CHECK ( salary  >=  100 AND  salary <= 100000));

CREATE TABLE IF NOT EXISTS  client (
               id INT AUTO_INCREMENT PRIMARY KEY,
               name VARCHAR(1000)    NOT  NULL  CHECK ( LENGTH (name)>=2 ));

CREATE TABLE IF NOT EXISTS  project (
               id INT PRIMARY KEY,
               client_id  INT ,
               start_date DATE,
               finish_date DATE);

CREATE TABLE IF NOT EXISTS   project_worker (
               project_id BIGINT,
               worker_id BIGINT,
               PRIMARY KEY (project_id, worker_id ));

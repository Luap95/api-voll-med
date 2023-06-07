CREATE TABLE agendamentos (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  paciente_id BIGINT,
  medico_id BIGINT,
  horario DATETIME,
  FOREIGN KEY (paciente_id) REFERENCES pacientes(id),
  FOREIGN KEY (medico_id) REFERENCES medicos(id)
);
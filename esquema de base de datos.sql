


CREATE TABLE `documento` (
  `id` int(11) DEFAULT NULL,
  `titulo` varchar(150) NOT NULL,
  `texto` varchar(20001) DEFAULT NULL,
  PRIMARY KEY (`titulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `termino` (
  `palabra` varchar(75) NOT NULL,
  `apaTotal` int(11) DEFAULT NULL,
  `numeroDeDocsEnQueAparece` int(11) DEFAULT NULL,
  `idf` double DEFAULT NULL,
  PRIMARY KEY (`palabra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE TABLE `frecuencia` (
  `palabra` varchar(75) NOT NULL,
  `titulo` varchar(75) NOT NULL,
  `repeticionesEnDocumento` int(11) DEFAULT NULL,
  `tfidf` double DEFAULT NULL,
  PRIMARY KEY (`palabra`,`titulo`),
  KEY `titulo_idx` (`titulo`),
  CONSTRAINT `palabra` FOREIGN KEY (`palabra`) REFERENCES `termino` (`palabra`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `titulo` FOREIGN KEY (`titulo`) REFERENCES `documento` (`titulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `consulta` (
  `idconsulta` int(11) NOT NULL AUTO_INCREMENT,
  `palabra` varchar(75) NOT NULL,
  `tf` int(11) DEFAULT NULL,
  PRIMARY KEY (`idconsulta`,`palabra`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


CREATE TABLE `peso_q` (
  `peso` double NOT NULL,
  `idconsulta` int(11) DEFAULT NULL,
  PRIMARY KEY (`peso`),
  KEY `idConsulta_idx` (`idconsulta`),
  CONSTRAINT `id` FOREIGN KEY (`idconsulta`) REFERENCES `consulta` (`idconsulta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `pesos_docs` (
  `titulo` varchar(75) NOT NULL,
  `pesos` double DEFAULT NULL,
  `idConsulta` int(11) DEFAULT NULL,
  PRIMARY KEY (`titulo`),
  KEY `idConsulta_idx` (`idConsulta`),
  CONSTRAINT `idConsulta` FOREIGN KEY (`idConsulta`) REFERENCES `consulta` (`idconsulta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tituloid` FOREIGN KEY (`titulo`) REFERENCES `documento` (`titulo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE DATABASE  IF NOT EXISTS `ecommerce` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ecommerce`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `ecommerce`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `carrinho`
--

CREATE TABLE IF NOT EXISTS `carrinho` (
  `id` int(11) NOT NULL,
  `data_carrinho` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `categoriaproduto`
--

CREATE TABLE IF NOT EXISTS `categoriaproduto` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `ativo` bit(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `categoriaproduto`
--

INSERT INTO `categoriaproduto` (`codigo`, `nome`, `descricao`, `ativo`) VALUES
(1, 'Eletronicos', 'Eletros', b'1'),
(2, 'Gamers', 'Games e acessorios', b'1'),
(3, 'Others', 'qualquer coisa', b'0'),
(4, 'Alterado', 'sdasdasd', b'1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `contato`
--

CREATE TABLE IF NOT EXISTS `contato` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `assunto` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `descricao` text,
  `protocolo_venda` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `desconto`
--

CREATE TABLE IF NOT EXISTS `desconto` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `faixa_valor` float DEFAULT NULL,
  `desconto` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `endereco`
--

CREATE TABLE IF NOT EXISTS `endereco` (
  `codigo` int(11) NOT NULL,
  `rua` varchar(45) NOT NULL,
  `numero` int(11) NOT NULL,
  `complemento` varchar(100) NOT NULL,
  `bairro` varchar(45) NOT NULL,
  `cidade` varchar(45) NOT NULL,
  `cep` varchar(9) NOT NULL,
  `idPessoa` int(11) NOT NULL,
  `estado` varchar(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `endereco`
--

INSERT INTO `endereco` (`codigo`, `rua`, `numero`, `complemento`, `bairro`, `cidade`, `cep`, `idPessoa`, `estado`) VALUES
(1, 'rua o jonny', 1058, 'afaasfafasfasf', 'Estreito', 'Florianopolis', '88070-020', 8, 'SC'),
(2, 'Av. das Rendeiras', 33, 'Bar do boni', 'Lagoa da Conceição', 'Florianópolis', '88061-423', 9, 'SC'),
(3, 'Rua de Biguá', 2231, 'Passando pelos pastos', 'Fundos', 'Biguaçu', '88160-000', 10, 'SC'),
(5, 'Rua Santos Saraiva', 88, 'uaheuahse', 'Capoeiras', 'Florianópolis', '88040-555', 13, 'SC'),
(6, 'rua Pedro Cunha', 111, 'Casa', 'Capoeiras', 'FLorianópolis', '88180-000', 14, 'SC');

-- --------------------------------------------------------

--
-- Estrutura da tabela `fotosproduto`
--

CREATE TABLE IF NOT EXISTS `fotosproduto` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `caminho` varchar(150) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `tamanho` int(11) NOT NULL,
  `idProduto` int(11) NOT NULL,
  `principal` bit(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `fotosproduto`
--

INSERT INTO `fotosproduto` (`codigo`, `nome`, `caminho`, `tipo`, `tamanho`, `idProduto`, `principal`) VALUES
(20, 'produto1.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto1.png','.png', 126997, 18, b'1'),
(21, 'produto2.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto2.png','.png', 233205, 19, b'1'),
(22, 'produto3.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto3.png', '.png', 136423, 20, b'1'),
(23, 'produto4.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto4.png', '.png', 158226, 21, b'1'),
(24, 'produto5.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto5.png', '.png', 351369, 22, b'1'),
(25, 'produto6.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto6.png', '.png', 128738, 18, b'0'),
(26, 'produto7.png', 'C:\Users\jonat\Documents\Ecommerce\ecommerce_senac_2020\web\imagensSistema\produto7.png', '.png', 390222, 24, b'1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `marca`
--

CREATE TABLE IF NOT EXISTS `marca` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `ativo` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `marca`
--

INSERT INTO `marca` (`codigo`, `nome`, `descricao`, `ativo`) VALUES
(1, 'Motorola', 'Celular', 1),
(2, 'Lenovo', 'Notebook', 1),
(3, 'Microsoft', 'CPU', 1),
(4, 'Razer', 'teclado', 0),
(5, 'xing ling', 'china', 1),
(6, 'sdawdad', 'asdasd', 1),
(7, 'Aletrado', 'dasdasd', 1),
(8, 'Aletrado', 'dasdasd', 0),
(9, 'Aletrado', 'dasdasd', 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `pedido`
--

CREATE TABLE IF NOT EXISTS `pedido` (
  `idProduto` int(11) NOT NULL,
  `idVenda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `pedido`
--

INSERT INTO `pedido` (`idProduto`, `idVenda`) VALUES
(6, 1),
(7, 1),
(21, 14),
(22, 14),
(20, 15),
(19, 16),
(24, 16),
(22, 17),
(20, 18),
(19, 19),
(19, 20),
(19, 21),
(19, 22),
(19, 23),
(21, 24),
(21, 25),
(20, 26),
(20, 27),
(20, 28),
(20, 29),
(20, 30),
(19, 31),
(20, 32),
(22, 32),
(20, 33),
(22, 33),
(20, 34),
(22, 34),
(20, 35),
(22, 35),
(19, 36);

-- --------------------------------------------------------

--
-- Estrutura da tabela `pessoa`
--

CREATE TABLE IF NOT EXISTS `pessoa` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cpfCnpj` varchar(14) NOT NULL,
  `sexo` char(1) NOT NULL,
  `dataNascimento` date DEFAULT NULL,
  `dddRes` int(2) NOT NULL,
  `telRes` int(9) NOT NULL,
  `dddCel` int(2) DEFAULT NULL,
  `telCel` int(9) DEFAULT NULL,
  `dataCadastro` datetime DEFAULT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `pessoa`
--

INSERT INTO `pessoa` (`codigo`, `nome`, `cpfCnpj`, `sexo`, `dataNascimento`, `dddRes`, `telRes`, `dddCel`, `telCel`, `dataCadastro`, `idUsuario`) VALUES
(8, 'Kevin Guimaraes', '331.231.859-91', 'M', '2015-11-20', 48, 33445474, 0, 0, '2015-11-28 00:00:00', 11),
(9, 'Rafael Biguá', '879.456.232-21', 'M', '2015-12-22', 47, 33562455, 48, 999999, '2015-12-12 00:00:00', 12),
(10, 'Julian Cirino', '222.222.222-22', 'M', '2015-12-28', 48, 33074521, 48, 84652565, '2015-12-13 00:00:00', 13),
(13, 'Natalia Ferreira', '000.000.000-00', 'F', '2015-12-21', 48, 32323319, 48, 99659764, '2015-12-13 00:00:00', 16),
(14, 'Gustavo Sanches', '211.111.111-11', 'M', '2006-02-14', 11, 21212121, 21, 21212121, '2015-12-14 00:00:00', 17);

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

CREATE TABLE IF NOT EXISTS `produto` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(45) DEFAULT NULL,
  `quantidade` int(11) NOT NULL,
  `valorCompra` float DEFAULT NULL,
  `valorVenda` float NOT NULL,
  `acessos` int(11) DEFAULT NULL,
  `dataCadastro` datetime DEFAULT NULL,
  `idCategoriaProduto` int(11) NOT NULL,
  `idMarca` int(11) NOT NULL,
  `ativo` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`codigo`, `nome`, `descricao`, `quantidade`, `valorCompra`, `valorVenda`, `acessos`, `dataCadastro`, `idCategoriaProduto`, `idMarca`, `ativo`) VALUES
(6, 'Moto G Plus', 'Celular Motorola 32 GB', 10, 1000, 1400, 2, '2015-11-24 00:00:00', 2, 1, b'0'),
(7, 'Xbox One', 'Console Xbox One 1T', 6, 1300, 1800, 5, '2015-11-24 00:00:00', 2, 3, b'1'),
(18, 'Fone wirelles', 'Fone de ouvido sem fio', 3, 100, 150, 6, '2015-11-24 00:00:00', 1, 1, b'1'),
(19, 'Computador de Mesa', 'Desktop completo', 3, 1000, 1200, 43, '2015-11-24 00:00:00', 1, 1, b'1'),
(20, 'Teclado Gamer', 'Teclado semi-mecanico', 3, 400, 800, 40, '2015-11-24 00:00:00', 1, 1, b'1'),
(21, 'prod teste img 4', 'ewweqewq', 3, 5000, 8000, 21, '2015-11-24 00:00:00', 1, 2, b'1'),
(22, 'adasd', 'ijeaopaepoaope', 100, 200, 600, 39, '2015-11-26 00:00:00', 1, 1, b'1'),
(23, 'Aletrado dinovo', 'wqweqwe', 150, 200, 600, 0, '2015-11-26 00:00:00', 1, 1, b'0'),
(24, 'Produto novo', 'sdasdasdasdasd', 10, 500, 560, 5, '2015-12-09 00:00:00', 1, 3, b'1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `status`
--

CREATE TABLE IF NOT EXISTS `status` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` varchar(50) DEFAULT NULL,
  `ativo` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `status`
--

INSERT INTO `status` (`codigo`, `nome`, `descricao`, `ativo`) VALUES
(1, 'Pendente', 'pendente', b'1'),
(2, 'Aprovada', 'aprovada', b'1'),
(3, 'Reprovada', 'reprovada', b'1'),
(4, 'Enviada', 'enviada', b'1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipousuario`
--

CREATE TABLE IF NOT EXISTS `tipousuario` (
  `codigo` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `descricao` text,
  `ativo` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `codigo` int(11) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha` varchar(45) NOT NULL,
  `ultimoAcesso` date DEFAULT NULL,
  `tipoUsuario` varchar(30) NOT NULL,
  `ativo` bit(1) NOT NULL DEFAULT b'1'
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`codigo`, `email`, `senha`, `ultimoAcesso`, `tipoUsuario`, `ativo`) VALUES
(11, 'email', '21232f297a57a5a743894a0e4a801fc3', NULL, 'admin', b'1'),
(12, 'gustavo.agostinho0@gmail.com', '21232f297a57a5a743894a0e4a801fc3', NULL, 'usuario', b'1'),
(13, 'novo@novo.com', '21232f297a57a5a743894a0e4a801fc3', NULL, 'usuario', b'1'),
(16, 'contato@pousadaflamboyant.net', 'e684f932e8c1bf8b8c2680176a2d53a8', NULL, 'usuario', b'1'),
(17, 'carlinhoanc@gmail.com', '481afbd5c650017c4978e8e4e0a3533e', NULL, 'usuario', b'1'),
(18, 'jonatan_ds@msn.com', '827ccb0eea8a706c4c34a16891f84e7b', NULL, 'admin', b'1');

-- --------------------------------------------------------

--
-- Estrutura da tabela `venda`
--

CREATE TABLE IF NOT EXISTS `venda` (
  `codigo` int(11) NOT NULL,
  `protocolo` varchar(45) NOT NULL,
  `dataVenda` datetime NOT NULL,
  `valorTotal` double NOT NULL,
  `idPessoa` int(11) NOT NULL,
  `idStatus` int(11) NOT NULL,
  `boletoCartao` varchar(45) DEFAULT NULL,
  `numeroBoletoCartao` varchar(45) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `venda`
--

INSERT INTO `venda` (`codigo`, `protocolo`, `dataVenda`, `valorTotal`, `idPessoa`, `idStatus`, `boletoCartao`, `numeroBoletoCartao`) VALUES
(1, '0123456', '2015-12-10 00:00:00', 30, 8, 4, NULL, NULL),
(14, '200054466111', '2015-12-14 00:00:00', 36, 9, 1, 'Cartão', '33444556667730000888'),
(15, '35712', '2015-12-14 00:00:00', 33, 9, 1, 'Cartão', '33444556667730000888'),
(16, '1450094384323', '2015-12-14 00:00:00', 1760, 9, 1, 'Cartão', '33444556667730000888'),
(17, '1450095817409', '2015-12-14 00:00:00', 3, 9, 1, 'Boleto', '33444556667730000888'),
(18, '1450095870660', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '33444556667730000888'),
(19, '1450096110751', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '33444556667730000888'),
(20, '1450096164261', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '33444556667730000888'),
(21, '1450096826193', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '33444556667730000888'),
(22, '1450097862351', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '33444556667730000888'),
(23, '1450098011320', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '33444556667730000888'),
(24, '1450098317568', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '33444556667730000888'),
(25, '1450098802583', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '33444556667730000888'),
(26, '1450138411591', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '1450138411591'),
(27, '1450138526672', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '1450138526672'),
(28, '1450138597545', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '1450138597545'),
(29, '1450138647391', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '1450138647391'),
(30, '1450138760770', '2015-12-14 00:00:00', 33, 9, 1, 'Boleto', '1450138760770'),
(31, '1450139048576', '2015-12-14 00:00:00', 1200, 9, 1, 'Boleto', '1450139048576'),
(32, '1450142174797', '2015-12-14 00:00:00', 39, 14, 1, 'Boleto', '1450142174797'),
(33, '1450142187716', '2015-12-14 00:00:00', 39, 14, 1, 'Boleto', '1450142187716'),
(34, '1450142270541', '2015-12-14 00:00:00', 39, 14, 1, 'Boleto', '1450142270541'),
(35, '1450142362004', '2015-12-14 00:00:00', 39, 14, 1, 'Boleto', '1450142362004'),
(36, '1450143521510', '2015-12-14 00:00:00', 2400, 14, 1, 'Boleto', '1450143521510');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carrinho`
--
ALTER TABLE `carrinho`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categoriaproduto`
--
ALTER TABLE `categoriaproduto`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `contato`
--
ALTER TABLE `contato`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `desconto`
--
ALTER TABLE `desconto`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`codigo`), ADD KEY `fk_endereco_pessoa1_idx` (`idPessoa`);

--
-- Indexes for table `fotosproduto`
--
ALTER TABLE `fotosproduto`
  ADD PRIMARY KEY (`codigo`), ADD KEY `fotoProduto_produtp_idx` (`idProduto`);

--
-- Indexes for table `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`idProduto`,`idVenda`), ADD KEY `fk_produto_has_venda_venda1_idx` (`idVenda`), ADD KEY `fk_produto_has_venda_produto1_idx` (`idProduto`);

--
-- Indexes for table `pessoa`
--
ALTER TABLE `pessoa`
  ADD PRIMARY KEY (`codigo`), ADD KEY `fk_pessoa_usuario1_idx` (`idUsuario`);

--
-- Indexes for table `produto`
--
ALTER TABLE `produto`
  ADD PRIMARY KEY (`codigo`), ADD KEY `fk_produto_categoria_produto1_idx` (`idCategoriaProduto`), ADD KEY `fk_produto_marca1_idx` (`idMarca`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `tipousuario`
--
ALTER TABLE `tipousuario`
  ADD PRIMARY KEY (`codigo`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codigo`), ADD UNIQUE KEY `email_UNIQUE` (`email`), ADD KEY `fk_usuario_tipo_user_idx` (`tipoUsuario`);

--
-- Indexes for table `venda`
--
ALTER TABLE `venda`
  ADD PRIMARY KEY (`codigo`), ADD KEY `fk_venda_pessoa1_idx` (`idPessoa`), ADD KEY `fk_venda_status` (`idStatus`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carrinho`
--
ALTER TABLE `carrinho`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `categoriaproduto`
--
ALTER TABLE `categoriaproduto`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `contato`
--
ALTER TABLE `contato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `endereco`
--
ALTER TABLE `endereco`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `fotosproduto`
--
ALTER TABLE `fotosproduto`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `marca`
--
ALTER TABLE `marca`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `pessoa`
--
ALTER TABLE `pessoa`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `produto`
--
ALTER TABLE `produto`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tipousuario`
--
ALTER TABLE `tipousuario`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `venda`
--
ALTER TABLE `venda`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=37;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `fotosproduto`
--
ALTER TABLE `fotosproduto`
ADD CONSTRAINT `fotoProduto_produtp` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `pedido`
--
ALTER TABLE `pedido`
ADD CONSTRAINT `fk_produto_has_venda_produto` FOREIGN KEY (`idProduto`) REFERENCES `produto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_produto_has_venda_venda` FOREIGN KEY (`idVenda`) REFERENCES `venda` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `pessoa`
--
ALTER TABLE `pessoa`
ADD CONSTRAINT `fk_pessoa_usuario` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `produto`
--
ALTER TABLE `produto`
ADD CONSTRAINT `fk_produto_categoria_produto` FOREIGN KEY (`idCategoriaProduto`) REFERENCES `categoriaproduto` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_produto_marca` FOREIGN KEY (`idMarca`) REFERENCES `marca` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limitadores para a tabela `venda`
--
ALTER TABLE `venda`
ADD CONSTRAINT `fk_venda_pessoa` FOREIGN KEY (`idPessoa`) REFERENCES `pessoa` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_venda_status` FOREIGN KEY (`idStatus`) REFERENCES `status` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;



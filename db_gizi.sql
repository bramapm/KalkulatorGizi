-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 22 Mar 2017 pada 13.49
-- Versi Server: 10.1.16-MariaDB
-- PHP Version: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_gizi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `makanan`
--

CREATE TABLE `makanan` (
  `id_makanan` int(11) NOT NULL,
  `nama_makanan` varchar(50) NOT NULL,
  `jenis` varchar(50) NOT NULL,
  `kkal` double NOT NULL,
  `karbo` double NOT NULL,
  `protein` double NOT NULL,
  `lemak` double NOT NULL,
  `foto` varchar(50) NOT NULL,
  `keterangan` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `makanan`
--

INSERT INTO `makanan` (`id_makanan`, `nama_makanan`, `jenis`, `kkal`, `karbo`, `protein`, `lemak`, `foto`, `keterangan`) VALUES
(1, 'margarin', 'pelengkap', 733, 3, 1, 81, 'foto margarin', 'keterangan margarin'),
(2, 'santan', 'pelengkap', 348, 1, 4, 34, 'foto santan', 'keterangan santan'),
(3, 'lemak sapi', 'pelengkap', 816, 3, 2, 90, 'foto', 'keterangan lemak sapi'),
(4, 'minyak ikan', 'lemak', 900, 4, 0, 100, 'foto minyak ikan', 'ketrngan minyak ikan'),
(5, 'minyak kelapa', 'lemak', 886, 4, 1, 98, 'foto minyak kelapa', 'keterangan minyak kelapa'),
(6, 'minyak kacang tanah', 'lemak', 900, 0, 0, 100, 'foto', 'ket'),
(7, 'minyak wijen', 'lemak', 898, 4, 0, 100, 'foto', 'ketetr'),
(8, 'mie ayam', 'makanan', 105, 11, 6, 4, 'fo', 'keterangan'),
(9, 'apel', 'buah', 68, 15, 0, 0, 'fo', 'fo'),
(10, 'alpukat', 'buah', 96, 8, 1, 7, '', ''),
(11, 'pisang ambon', 'buah', 112, 24, 1, 1, '', ''),
(12, 'melon', 'buah', 36, 7, 1, 0, '', ''),
(13, 'nasi goreng', 'makanan', 178, 25, 3, 7, '', ''),
(14, 'sayur bayam', 'resep', 18, 2, 2, 0, '', ''),
(15, 'tumis sawi', 'resep', 32, 4, 2, 0, '', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `record`
--

CREATE TABLE `record` (
  `id_record` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_makanan` varchar(50) NOT NULL,
  `id_olahraga` varchar(50) NOT NULL,
  `kat_waktu` enum('pagi','siang','malam','') NOT NULL,
  `tanggal` date NOT NULL,
  `kal_lebih` float NOT NULL,
  `kal_kurang` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nama_user` varchar(50) NOT NULL,
  `jk` enum('L','P') NOT NULL,
  `ttl` date NOT NULL,
  `tinggi` int(11) NOT NULL,
  `berat` double NOT NULL,
  `umur` int(11) NOT NULL,
  `status` enum('admin','member','','') NOT NULL,
  `kalori` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `username`, `password`, `email`, `nama_user`, `jk`, `ttl`, `tinggi`, `berat`, `umur`, `status`, `kalori`) VALUES
(1, 'bapm', 'bapm', 'pradttu@gmail.com', 'Bramwiratma Arya PM', 'L', '1996-04-22', 169, 55, 21, 'admin', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `makanan`
--
ALTER TABLE `makanan`
  ADD PRIMARY KEY (`id_makanan`);

--
-- Indexes for table `record`
--
ALTER TABLE `record`
  ADD PRIMARY KEY (`id_record`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `makanan`
--
ALTER TABLE `makanan`
  MODIFY `id_makanan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `record`
--
ALTER TABLE `record`
  MODIFY `id_record` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

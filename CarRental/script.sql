USE [master]
GO
/****** Object:  Database [lab231_3]    Script Date: 4/24/2021 11:00:50 PM ******/
CREATE DATABASE [lab231_3]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'lab231_3', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\lab231_3.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'lab231_3_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\lab231_3_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [lab231_3] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [lab231_3].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [lab231_3] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [lab231_3] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [lab231_3] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [lab231_3] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [lab231_3] SET ARITHABORT OFF 
GO
ALTER DATABASE [lab231_3] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [lab231_3] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [lab231_3] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [lab231_3] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [lab231_3] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [lab231_3] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [lab231_3] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [lab231_3] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [lab231_3] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [lab231_3] SET  DISABLE_BROKER 
GO
ALTER DATABASE [lab231_3] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [lab231_3] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [lab231_3] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [lab231_3] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [lab231_3] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [lab231_3] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [lab231_3] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [lab231_3] SET RECOVERY FULL 
GO
ALTER DATABASE [lab231_3] SET  MULTI_USER 
GO
ALTER DATABASE [lab231_3] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [lab231_3] SET DB_CHAINING OFF 
GO
ALTER DATABASE [lab231_3] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [lab231_3] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [lab231_3] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'lab231_3', N'ON'
GO
ALTER DATABASE [lab231_3] SET QUERY_STORE = OFF
GO
USE [lab231_3]
GO
/****** Object:  Table [dbo].[tbl_cars]    Script Date: 4/24/2021 11:00:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_cars](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[color] [nvarchar](50) NOT NULL,
	[year] [int] NOT NULL,
	[category] [nchar](10) NOT NULL,
	[price] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[rental_date] [int] NOT NULL,
	[create_date] [date] NOT NULL,
 CONSTRAINT [PK_tbl_cars] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_cars_in_history_payment]    Script Date: 4/24/2021 11:00:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_cars_in_history_payment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[history_id] [nvarchar](50) NOT NULL,
	[car_id] [int] NOT NULL,
	[expired_date] [date] NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [int] NOT NULL,
 CONSTRAINT [PK_tbl_cars_in_history_payment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_discount_coupons]    Script Date: 4/24/2021 11:00:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_discount_coupons](
	[id] [nvarchar](10) NOT NULL,
	[discount] [int] NOT NULL,
	[expired_date] [date] NULL,
 CONSTRAINT [PK_tbl_discount_coupons] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_history]    Script Date: 4/24/2021 11:00:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_history](
	[id] [nvarchar](50) NOT NULL,
	[total_price] [int] NOT NULL,
	[order_date] [date] NOT NULL,
	[status] [bit] NOT NULL,
	[user_id] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tbl_history] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_users]    Script Date: 4/24/2021 11:00:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_users](
	[email] [nvarchar](50) NOT NULL,
	[phone] [nvarchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[address] [nvarchar](50) NOT NULL,
	[status] [nvarchar](10) NOT NULL,
	[role] [nvarchar](50) NOT NULL,
	[create_date] [date] NOT NULL,
	[password] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tbl_users] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_cars] ON 

INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (1, N'civic', N'green', 2010, N'1         ', 10000, 496, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (2, N'gtr', N'yello', 1998, N'2         ', 200000, 499, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (3, N'morning', N'black', 2012, N'2         ', 10000, 499, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (4, N'asd', N'red', 2013, N'1         ', 566, 500, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (5, N'zxc', N'blue', 2010, N'1         ', 754, 500, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (6, N'vbn', N'pink', 2019, N'1         ', 1234, 499, 3, CAST(N'2021-04-23' AS Date))
INSERT [dbo].[tbl_cars] ([id], [name], [color], [year], [category], [price], [quantity], [rental_date], [create_date]) VALUES (21, N'cmm', N'gray', 2019, N'1         ', 123123, 499, 3, CAST(N'2021-04-23' AS Date))
SET IDENTITY_INSERT [dbo].[tbl_cars] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_cars_in_history_payment] ON 

INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (8, N'd9a27daa-5389-493d-8920-58931eb94e74', 1, CAST(N'2021-04-27' AS Date), 3, 10000)
INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (9, N'326ccd9e-39aa-4f36-88fa-1beec774c947', 1, CAST(N'2021-04-27' AS Date), 1, 10000)
INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (10, N'9eb3c6cd-3ec1-4bb3-ad18-85179685c506', 2, CAST(N'2021-04-27' AS Date), 1, 200000)
INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (11, N'9dc68542-6000-48ac-a92b-2fcc3ee620a0', 3, CAST(N'2021-04-27' AS Date), 1, 10000)
INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (12, N'9dc68542-6000-48ac-a92b-2fcc3ee620a0', 21, CAST(N'2021-04-27' AS Date), 1, 123123)
INSERT [dbo].[tbl_cars_in_history_payment] ([id], [history_id], [car_id], [expired_date], [quantity], [price]) VALUES (13, N'c2157b38-b27a-4f2c-9072-0da78f6ce102', 6, CAST(N'2021-04-27' AS Date), 1, 1234)
SET IDENTITY_INSERT [dbo].[tbl_cars_in_history_payment] OFF
GO
INSERT [dbo].[tbl_discount_coupons] ([id], [discount], [expired_date]) VALUES (N'asd', 10, CAST(N'2021-04-26' AS Date))
GO
INSERT [dbo].[tbl_history] ([id], [total_price], [order_date], [status], [user_id]) VALUES (N'326ccd9e-39aa-4f36-88fa-1beec774c947', 9000, CAST(N'2021-04-23' AS Date), 0, N'cnviety2@gmail.com')
INSERT [dbo].[tbl_history] ([id], [total_price], [order_date], [status], [user_id]) VALUES (N'9dc68542-6000-48ac-a92b-2fcc3ee620a0', 133123, CAST(N'2021-04-22' AS Date), 0, N'cnviety2@gmail.com')
INSERT [dbo].[tbl_history] ([id], [total_price], [order_date], [status], [user_id]) VALUES (N'9eb3c6cd-3ec1-4bb3-ad18-85179685c506', 180000, CAST(N'2021-04-24' AS Date), 1, N'cnviety2@gmail.com')
INSERT [dbo].[tbl_history] ([id], [total_price], [order_date], [status], [user_id]) VALUES (N'c2157b38-b27a-4f2c-9072-0da78f6ce102', 1234, CAST(N'2021-04-24' AS Date), 1, N'cnviety2@gmail.com')
INSERT [dbo].[tbl_history] ([id], [total_price], [order_date], [status], [user_id]) VALUES (N'd9a27daa-5389-493d-8920-58931eb94e74', 30000, CAST(N'2021-04-24' AS Date), 1, N'cnviety2@gmail.com')
GO
INSERT [dbo].[tbl_users] ([email], [phone], [name], [address], [status], [role], [create_date], [password]) VALUES (N'cnviety2@gmail.com', N'0912589485', N'tran dinh duy', N'194/33 lac long quan', N'Active', N'ROLE_USER', CAST(N'2021-04-22' AS Date), N'123')
GO
ALTER TABLE [dbo].[tbl_cars_in_history_payment]  WITH CHECK ADD  CONSTRAINT [FK_tbl_cars_in_history_payment_tbl_cars] FOREIGN KEY([car_id])
REFERENCES [dbo].[tbl_cars] ([id])
GO
ALTER TABLE [dbo].[tbl_cars_in_history_payment] CHECK CONSTRAINT [FK_tbl_cars_in_history_payment_tbl_cars]
GO
ALTER TABLE [dbo].[tbl_cars_in_history_payment]  WITH CHECK ADD  CONSTRAINT [FK_tbl_cars_in_history_payment_tbl_history] FOREIGN KEY([history_id])
REFERENCES [dbo].[tbl_history] ([id])
GO
ALTER TABLE [dbo].[tbl_cars_in_history_payment] CHECK CONSTRAINT [FK_tbl_cars_in_history_payment_tbl_history]
GO
ALTER TABLE [dbo].[tbl_history]  WITH CHECK ADD  CONSTRAINT [FK_tbl_history_tbl_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_users] ([email])
GO
ALTER TABLE [dbo].[tbl_history] CHECK CONSTRAINT [FK_tbl_history_tbl_users]
GO
USE [master]
GO
ALTER DATABASE [lab231_3] SET  READ_WRITE 
GO

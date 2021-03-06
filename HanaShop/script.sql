USE [master]
GO
/****** Object:  Database [lab231]    Script Date: 4/15/2021 7:06:59 AM ******/
CREATE DATABASE [lab231]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'lab231', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\lab231.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'lab231_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\lab231_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [lab231] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [lab231].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [lab231] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [lab231] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [lab231] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [lab231] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [lab231] SET ARITHABORT OFF 
GO
ALTER DATABASE [lab231] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [lab231] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [lab231] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [lab231] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [lab231] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [lab231] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [lab231] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [lab231] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [lab231] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [lab231] SET  DISABLE_BROKER 
GO
ALTER DATABASE [lab231] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [lab231] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [lab231] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [lab231] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [lab231] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [lab231] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [lab231] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [lab231] SET RECOVERY FULL 
GO
ALTER DATABASE [lab231] SET  MULTI_USER 
GO
ALTER DATABASE [lab231] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [lab231] SET DB_CHAINING OFF 
GO
ALTER DATABASE [lab231] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [lab231] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [lab231] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'lab231', N'ON'
GO
ALTER DATABASE [lab231] SET QUERY_STORE = OFF
GO
USE [lab231]
GO
/****** Object:  Table [dbo].[tbl_categories]    Script Date: 4/15/2021 7:07:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_categories](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[code] [nchar](10) NOT NULL,
 CONSTRAINT [PK_tbl_categories] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_foods]    Script Date: 4/15/2021 7:07:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_foods](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[image] [nvarchar](100) NOT NULL,
	[price] [int] NOT NULL,
	[category] [nchar](10) NOT NULL,
	[quantity] [int] NOT NULL,
	[status] [bit] NOT NULL,
	[create_date] [datetime] NULL,
	[update_date] [datetime] NULL,
	[update_user] [nvarchar](50) NULL,
 CONSTRAINT [PK_tbl_foods] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_foods_in_a_payment]    Script Date: 4/15/2021 7:07:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_foods_in_a_payment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[payment_id] [nvarchar](50) NOT NULL,
	[food_id] [int] NOT NULL,
	[food_price] [int] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_tbl_foods_in_a_payment] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_payments]    Script Date: 4/15/2021 7:07:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_payments](
	[id] [nvarchar](50) NOT NULL,
	[payment_date] [datetime] NOT NULL,
	[user_id] [nvarchar](50) NOT NULL,
	[total_price] [int] NOT NULL,
 CONSTRAINT [PK_tbl_payments] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_users]    Script Date: 4/15/2021 7:07:00 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [nvarchar](50) NOT NULL,
	[password] [nvarchar](50) NOT NULL,
	[role] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK_tbl_users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_categories] ON 

INSERT [dbo].[tbl_categories] ([id], [name], [code]) VALUES (1, N'Cheap food', N'CF        ')
INSERT [dbo].[tbl_categories] ([id], [name], [code]) VALUES (2, N'Drink', N'DR        ')
SET IDENTITY_INSERT [dbo].[tbl_categories] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_foods] ON 

INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (2, N'Apple676', N'/images/no-image.png', 200, N'CF        ', 200, 1, NULL, CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'admin')
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (3, N'123', N'/images/no-image.png', 100, N'CF        ', 486, 1, CAST(N'2021-04-12T00:00:00.000' AS DateTime), CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'admin')
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (4, N'as2', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'DR        ', 492, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (5, N'as3', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 497, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (6, N'as4', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'DR        ', 495, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (7, N'as5', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'DR        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (8, N'as6', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'DR        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (9, N'as7', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (10, N'as8', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (11, N'as9', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (12, N'as10', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (13, N'as11', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (14, N'as12', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (15, N'as12', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (16, N'as13', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (17, N'as14', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (18, N'as15', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (19, N'as16', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (20, N'as17', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (21, N'as18', N'/images/no-image.png', 123, N'CF        ', 900000, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), CAST(N'2021-04-13T00:00:00.000' AS DateTime), N'admin')
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (22, N'as19', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (23, N'as20', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (24, N'as21', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 499, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (25, N'as22', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (26, N'as23', N'/images/no-image.png', 20000, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), CAST(N'2021-04-13T00:00:00.000' AS DateTime), N'admin')
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (27, N'as24', N'/images/ed3f1089-0f5a-4ee8-a1e8-ef6ddf98a7ad.png', 123, N'CF        ', 500, 0, CAST(N'2021-04-13T00:00:00.000' AS DateTime), CAST(N'2021-04-13T00:00:00.000' AS DateTime), N'admin')
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (28, N'asddf', N'/images/no-image.png', 4000, N'CF        ', 500, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), NULL, NULL)
INSERT [dbo].[tbl_foods] ([id], [name], [image], [price], [category], [quantity], [status], [create_date], [update_date], [update_user]) VALUES (29, N'asdasd', N'/images/no-image.png', 600, N'DR        ', 497, 1, CAST(N'2021-04-13T00:00:00.000' AS DateTime), CAST(N'2021-04-13T00:00:00.000' AS DateTime), N'admin')
SET IDENTITY_INSERT [dbo].[tbl_foods] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_foods_in_a_payment] ON 

INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (5, N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', 2, 100, 1)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (6, N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', 3, 100, 3)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (7, N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', 4, 123, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (8, N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', 5, 123, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (9, N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', 6, 123, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (10, N'11b1c640-17ba-42b2-a81f-f55ce2c1e35a', 2, 100, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (11, N'11b1c640-17ba-42b2-a81f-f55ce2c1e35a', 4, 123, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (12, N'c10362ad-ffe0-41f8-91ab-b0a611e5b920', 3, 100, 1)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (13, N'b7d91864-e2b8-41fc-8e43-6dec0af72a27', 3, 100, 1)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (14, N'd0a6b8d0-a204-419d-b81b-40ee057b504c', 3, 100, 1)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (15, N'd0a6b8d0-a204-419d-b81b-40ee057b504c', 4, 123, 3)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (16, N'0e8c8dde-dd75-4517-b2d2-d633f9b53994', 2, 100, 2)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (17, N'0e8c8dde-dd75-4517-b2d2-d633f9b53994', 3, 100, 4)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (18, N'c823ae49-c8a8-413d-9bfb-61171e2b6703', 6, 123, 3)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (19, N'69864d09-01b1-4d49-8e27-b174253253d2', 24, 123, 1)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (20, N'f3ab0bd2-5299-43f9-946e-3603f5c54710', 29, 600, 3)
INSERT [dbo].[tbl_foods_in_a_payment] ([id], [payment_id], [food_id], [food_price], [quantity]) VALUES (21, N'd88874c7-02f7-434b-87a2-45aedef9a040', 2, 200, 1)
SET IDENTITY_INSERT [dbo].[tbl_foods_in_a_payment] OFF
GO
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'0e8c8dde-dd75-4517-b2d2-d633f9b53994', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'duytdse63047', 600)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'11b1c640-17ba-42b2-a81f-f55ce2c1e35a', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 446)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'42bb4a6a-c13d-43d5-8478-b8fc6730912d', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 1138)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'69864d09-01b1-4d49-8e27-b174253253d2', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 123)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'b7d91864-e2b8-41fc-8e43-6dec0af72a27', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 100)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'c10362ad-ffe0-41f8-91ab-b0a611e5b920', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 100)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'c823ae49-c8a8-413d-9bfb-61171e2b6703', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'duytdse63047', 369)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'd0a6b8d0-a204-419d-b81b-40ee057b504c', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 469)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'd88874c7-02f7-434b-87a2-45aedef9a040', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'duytdse63047', 200)
INSERT [dbo].[tbl_payments] ([id], [payment_date], [user_id], [total_price]) VALUES (N'f3ab0bd2-5299-43f9-946e-3603f5c54710', CAST(N'2021-04-14T00:00:00.000' AS DateTime), N'user', 1800)
GO
SET IDENTITY_INSERT [dbo].[tbl_users] ON 

INSERT [dbo].[tbl_users] ([id], [user_id], [password], [role]) VALUES (1, N'user', N'user', N'ROLE_USER')
INSERT [dbo].[tbl_users] ([id], [user_id], [password], [role]) VALUES (2, N'admin', N'admin', N'ROLE_ADMIN')
INSERT [dbo].[tbl_users] ([id], [user_id], [password], [role]) VALUES (4, N'admin2', N'admin', N'ROLE_ADMIN')
INSERT [dbo].[tbl_users] ([id], [user_id], [password], [role]) VALUES (5, N'duytdse63047', N'***', N'ROLE_USER')
SET IDENTITY_INSERT [dbo].[tbl_users] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_tbl_categories]    Script Date: 4/15/2021 7:07:00 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_tbl_categories] ON [dbo].[tbl_categories]
(
	[code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_tbl_users]    Script Date: 4/15/2021 7:07:00 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_tbl_users] ON [dbo].[tbl_users]
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbl_categories]  WITH CHECK ADD  CONSTRAINT [FK_tbl_categories_tbl_categories] FOREIGN KEY([id])
REFERENCES [dbo].[tbl_categories] ([id])
GO
ALTER TABLE [dbo].[tbl_categories] CHECK CONSTRAINT [FK_tbl_categories_tbl_categories]
GO
ALTER TABLE [dbo].[tbl_foods]  WITH CHECK ADD  CONSTRAINT [FK_tbl_foods_tbl_categories] FOREIGN KEY([update_user])
REFERENCES [dbo].[tbl_users] ([user_id])
GO
ALTER TABLE [dbo].[tbl_foods] CHECK CONSTRAINT [FK_tbl_foods_tbl_categories]
GO
ALTER TABLE [dbo].[tbl_foods]  WITH CHECK ADD  CONSTRAINT [FK_tbl_foods_tbl_foods] FOREIGN KEY([id])
REFERENCES [dbo].[tbl_foods] ([id])
GO
ALTER TABLE [dbo].[tbl_foods] CHECK CONSTRAINT [FK_tbl_foods_tbl_foods]
GO
ALTER TABLE [dbo].[tbl_foods_in_a_payment]  WITH CHECK ADD  CONSTRAINT [FK_tbl_foods_in_a_payment_tbl_payments] FOREIGN KEY([payment_id])
REFERENCES [dbo].[tbl_payments] ([id])
GO
ALTER TABLE [dbo].[tbl_foods_in_a_payment] CHECK CONSTRAINT [FK_tbl_foods_in_a_payment_tbl_payments]
GO
ALTER TABLE [dbo].[tbl_payments]  WITH CHECK ADD  CONSTRAINT [FK_tbl_payments_tbl_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[tbl_users] ([user_id])
GO
ALTER TABLE [dbo].[tbl_payments] CHECK CONSTRAINT [FK_tbl_payments_tbl_users]
GO
USE [master]
GO
ALTER DATABASE [lab231] SET  READ_WRITE 
GO

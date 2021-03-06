USE [lab231_2]
GO
/****** Object:  Table [dbo].[tbl_answer_of_user]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_answer_of_user](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[history_id] [nvarchar](50) NOT NULL,
	[question_id] [nvarchar](50) NOT NULL,
	[answer_id_of_user] [int] NOT NULL,
	[is_correct] [bit] NULL,
 CONSTRAINT [PK_tbl_answer_of_user] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_answers]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_answers](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[question_id] [nvarchar](50) NOT NULL,
	[answer_content] [nvarchar](50) NOT NULL,
	[is_correct] [bit] NOT NULL,
 CONSTRAINT [PK_tbl_answers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_history_quiz]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_history_quiz](
	[id] [nvarchar](50) NOT NULL,
	[create_date] [datetime] NOT NULL,
	[score] [int] NOT NULL,
	[user_email] [nvarchar](50) NOT NULL,
	[subject_id] [int] NOT NULL,
 CONSTRAINT [PK_tbl_history_quiz] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_questions]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_questions](
	[id] [nvarchar](50) NOT NULL,
	[question_content] [nvarchar](500) NOT NULL,
	[subject_id] [int] NOT NULL,
	[status] [bit] NOT NULL,
	[create_date] [datetime] NULL,
	[update_date] [datetime] NULL,
 CONSTRAINT [PK_tbl_questions] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_subjects]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_subjects](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[subject] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_tbl_subjects] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_users]    Script Date: 4/22/2021 10:04:51 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_users](
	[email] [nvarchar](50) NOT NULL,
	[name] [nchar](10) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[role] [nvarchar](50) NOT NULL,
	[status] [nchar](10) NOT NULL,
 CONSTRAINT [PK_tbl_users] PRIMARY KEY CLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[tbl_answer_of_user] ON 

INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (39, N'505dcca3-647e-421c-908a-6874f6b438bc', N'a5ab0e45-04f3-418a-9797-1825d77a2993', 71, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (40, N'505dcca3-647e-421c-908a-6874f6b438bc', N'475fda87-8799-4a91-8d09-3d706fbca2b6', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (41, N'505dcca3-647e-421c-908a-6874f6b438bc', N'e912827c-3620-43f9-a6d9-b30a785655cd', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (42, N'505dcca3-647e-421c-908a-6874f6b438bc', N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (43, N'505dcca3-647e-421c-908a-6874f6b438bc', N'9da07f51-c2e3-4231-a9c6-36190217312a', 61, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (44, N'a9453b84-3966-4df4-afb9-6f9eb6834439', N'd4395847-6d37-43de-89c1-37af19ad6f2e', 80, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (45, N'a9453b84-3966-4df4-afb9-6f9eb6834439', N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', 43, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (46, N'a9453b84-3966-4df4-afb9-6f9eb6834439', N'df1617a1-cf3b-4521-9813-69477d90dd08', 83, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (47, N'a9453b84-3966-4df4-afb9-6f9eb6834439', N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', 39, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (48, N'a9453b84-3966-4df4-afb9-6f9eb6834439', N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', 7, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (49, N'4232cb2b-1043-425f-825b-141ac02146da', N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', 44, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (50, N'4232cb2b-1043-425f-825b-141ac02146da', N'd4395847-6d37-43de-89c1-37af19ad6f2e', 80, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (51, N'4232cb2b-1043-425f-825b-141ac02146da', N'df1617a1-cf3b-4521-9813-69477d90dd08', 83, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (52, N'4232cb2b-1043-425f-825b-141ac02146da', N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', 39, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (53, N'4232cb2b-1043-425f-825b-141ac02146da', N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', 9, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (54, N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', N'98424dad-e962-47bc-a986-ed2f4ed41a6f', 67, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (55, N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', N'e912827c-3620-43f9-a6d9-b30a785655cd', 23, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (56, N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', N'8580d43f-e85a-4953-8ce2-34f77d3be06c', 47, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (57, N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', N'a723562b-9bf0-4718-ad9e-5d0f8788a344', 11, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (58, N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', N'1fc9443d-1858-4a1f-af8a-8438e85cd932', 52, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (59, N'64a5179b-acfb-46c8-87cd-6ef662de3029', N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (60, N'64a5179b-acfb-46c8-87cd-6ef662de3029', N'f2e41956-a528-4570-842f-1d4566753424', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (61, N'64a5179b-acfb-46c8-87cd-6ef662de3029', N'1fc9443d-1858-4a1f-af8a-8438e85cd932', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (62, N'64a5179b-acfb-46c8-87cd-6ef662de3029', N'9da07f51-c2e3-4231-a9c6-36190217312a', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (63, N'64a5179b-acfb-46c8-87cd-6ef662de3029', N'8ea4e9e1-21bc-469c-959c-d19792278460', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (64, N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', N'd4395847-6d37-43de-89c1-37af19ad6f2e', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (65, N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (66, N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (67, N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', N'b172fb7c-1188-4746-91d1-bed4ac10d247', 78, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (68, N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (69, N'9c993d45-cb63-40f4-8595-801e13d603d0', N'a5ab0e45-04f3-418a-9797-1825d77a2993', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (70, N'9c993d45-cb63-40f4-8595-801e13d603d0', N'475fda87-8799-4a91-8d09-3d706fbca2b6', 22, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (71, N'9c993d45-cb63-40f4-8595-801e13d603d0', N'98424dad-e962-47bc-a986-ed2f4ed41a6f', 70, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (72, N'9c993d45-cb63-40f4-8595-801e13d603d0', N'1fc9443d-1858-4a1f-af8a-8438e85cd932', 54, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (73, N'9c993d45-cb63-40f4-8595-801e13d603d0', N'8ea4e9e1-21bc-469c-959c-d19792278460', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (74, N'9b1fcd49-a005-429e-b46e-564d28d660ae', N'98424dad-e962-47bc-a986-ed2f4ed41a6f', 67, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (75, N'9b1fcd49-a005-429e-b46e-564d28d660ae', N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', 31, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (76, N'9b1fcd49-a005-429e-b46e-564d28d660ae', N'1fc9443d-1858-4a1f-af8a-8438e85cd932', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (77, N'9b1fcd49-a005-429e-b46e-564d28d660ae', N'9da07f51-c2e3-4231-a9c6-36190217312a', 59, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (78, N'9b1fcd49-a005-429e-b46e-564d28d660ae', N'52327bd4-6b28-4631-8df9-68788f3ae27b', -1, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (79, N'2fe60999-102e-41db-ab31-7977c3abd6c7', N'ad132af9-0436-4a73-962b-abed53920802', 15, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (80, N'2fe60999-102e-41db-ab31-7977c3abd6c7', N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', 31, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (81, N'2fe60999-102e-41db-ab31-7977c3abd6c7', N'a723562b-9bf0-4718-ad9e-5d0f8788a344', 14, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (82, N'2fe60999-102e-41db-ab31-7977c3abd6c7', N'9da07f51-c2e3-4231-a9c6-36190217312a', 59, NULL)
INSERT [dbo].[tbl_answer_of_user] ([id], [history_id], [question_id], [answer_id_of_user], [is_correct]) VALUES (83, N'2fe60999-102e-41db-ab31-7977c3abd6c7', N'52327bd4-6b28-4631-8df9-68788f3ae27b', -1, NULL)
SET IDENTITY_INSERT [dbo].[tbl_answer_of_user] OFF
GO
SET IDENTITY_INSERT [dbo].[tbl_answers] ON 

INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (3, N'52327bd4-6b28-4631-8df9-68788f3ae27b', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (4, N'52327bd4-6b28-4631-8df9-68788f3ae27b', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (5, N'52327bd4-6b28-4631-8df9-68788f3ae27b', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (6, N'52327bd4-6b28-4631-8df9-68788f3ae27b', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (7, N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (8, N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (9, N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (10, N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (11, N'a723562b-9bf0-4718-ad9e-5d0f8788a344', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (12, N'a723562b-9bf0-4718-ad9e-5d0f8788a344', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (13, N'a723562b-9bf0-4718-ad9e-5d0f8788a344', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (14, N'a723562b-9bf0-4718-ad9e-5d0f8788a344', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (15, N'ad132af9-0436-4a73-962b-abed53920802', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (16, N'ad132af9-0436-4a73-962b-abed53920802', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (17, N'ad132af9-0436-4a73-962b-abed53920802', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (18, N'ad132af9-0436-4a73-962b-abed53920802', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (19, N'475fda87-8799-4a91-8d09-3d706fbca2b6', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (20, N'475fda87-8799-4a91-8d09-3d706fbca2b6', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (21, N'475fda87-8799-4a91-8d09-3d706fbca2b6', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (22, N'475fda87-8799-4a91-8d09-3d706fbca2b6', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (23, N'e912827c-3620-43f9-a6d9-b30a785655cd', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (24, N'e912827c-3620-43f9-a6d9-b30a785655cd', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (25, N'e912827c-3620-43f9-a6d9-b30a785655cd', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (26, N'e912827c-3620-43f9-a6d9-b30a785655cd', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (27, N'7ae27011-f6d4-49ab-b86c-b23746148fe7', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (28, N'7ae27011-f6d4-49ab-b86c-b23746148fe7', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (29, N'7ae27011-f6d4-49ab-b86c-b23746148fe7', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (30, N'7ae27011-f6d4-49ab-b86c-b23746148fe7', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (31, N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (32, N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (33, N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (34, N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (35, N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (36, N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (37, N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (38, N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (39, N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (40, N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (41, N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (42, N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (43, N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (44, N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (45, N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (46, N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (47, N'8580d43f-e85a-4953-8ce2-34f77d3be06c', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (48, N'8580d43f-e85a-4953-8ce2-34f77d3be06c', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (49, N'8580d43f-e85a-4953-8ce2-34f77d3be06c', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (50, N'8580d43f-e85a-4953-8ce2-34f77d3be06c', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (51, N'1fc9443d-1858-4a1f-af8a-8438e85cd932', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (52, N'1fc9443d-1858-4a1f-af8a-8438e85cd932', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (53, N'1fc9443d-1858-4a1f-af8a-8438e85cd932', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (54, N'1fc9443d-1858-4a1f-af8a-8438e85cd932', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (55, N'f2e41956-a528-4570-842f-1d4566753424', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (56, N'f2e41956-a528-4570-842f-1d4566753424', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (57, N'f2e41956-a528-4570-842f-1d4566753424', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (58, N'f2e41956-a528-4570-842f-1d4566753424', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (59, N'9da07f51-c2e3-4231-a9c6-36190217312a', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (60, N'9da07f51-c2e3-4231-a9c6-36190217312a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (61, N'9da07f51-c2e3-4231-a9c6-36190217312a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (62, N'9da07f51-c2e3-4231-a9c6-36190217312a', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (63, N'8ea4e9e1-21bc-469c-959c-d19792278460', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (64, N'8ea4e9e1-21bc-469c-959c-d19792278460', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (65, N'8ea4e9e1-21bc-469c-959c-d19792278460', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (66, N'8ea4e9e1-21bc-469c-959c-d19792278460', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (67, N'98424dad-e962-47bc-a986-ed2f4ed41a6f', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (68, N'98424dad-e962-47bc-a986-ed2f4ed41a6f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (69, N'98424dad-e962-47bc-a986-ed2f4ed41a6f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (70, N'98424dad-e962-47bc-a986-ed2f4ed41a6f', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (71, N'a5ab0e45-04f3-418a-9797-1825d77a2993', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (72, N'a5ab0e45-04f3-418a-9797-1825d77a2993', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (73, N'a5ab0e45-04f3-418a-9797-1825d77a2993', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (74, N'a5ab0e45-04f3-418a-9797-1825d77a2993', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (75, N'b172fb7c-1188-4746-91d1-bed4ac10d247', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (76, N'b172fb7c-1188-4746-91d1-bed4ac10d247', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (77, N'b172fb7c-1188-4746-91d1-bed4ac10d247', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (78, N'b172fb7c-1188-4746-91d1-bed4ac10d247', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (79, N'd4395847-6d37-43de-89c1-37af19ad6f2e', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (80, N'd4395847-6d37-43de-89c1-37af19ad6f2e', N'true', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (81, N'd4395847-6d37-43de-89c1-37af19ad6f2e', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (82, N'd4395847-6d37-43de-89c1-37af19ad6f2e', N'false', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (83, N'df1617a1-cf3b-4521-9813-69477d90dd08', N'true random', 1)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (84, N'df1617a1-cf3b-4521-9813-69477d90dd08', N'false random', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (85, N'df1617a1-cf3b-4521-9813-69477d90dd08', N'lalala', 0)
INSERT [dbo].[tbl_answers] ([id], [question_id], [answer_content], [is_correct]) VALUES (86, N'df1617a1-cf3b-4521-9813-69477d90dd08', N'sai', 0)
SET IDENTITY_INSERT [dbo].[tbl_answers] OFF
GO
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'2fe60999-102e-41db-ab31-7977c3abd6c7', CAST(N'2021-04-22T00:00:00.000' AS DateTime), 3, N'new@new.com', 1)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'4232cb2b-1043-425f-825b-141ac02146da', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 3, N'duy@duy.com', 2)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'505dcca3-647e-421c-908a-6874f6b438bc', CAST(N'2021-04-20T00:00:00.000' AS DateTime), 1, N'duy@duy.com', 1)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'64a5179b-acfb-46c8-87cd-6ef662de3029', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 0, N'duy3@duy.com', 1)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'7b5721c0-98d0-4acf-bed1-7f4b36252eb4', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 0, N'duy@duy.com', 2)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'9b1fcd49-a005-429e-b46e-564d28d660ae', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 3, N'duy3@duy.com', 1)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'9c993d45-cb63-40f4-8595-801e13d603d0', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 0, N'duy3@duy.com', 1)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'a9453b84-3966-4df4-afb9-6f9eb6834439', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 5, N'duy3@duy.com', 2)
INSERT [dbo].[tbl_history_quiz] ([id], [create_date], [score], [user_email], [subject_id]) VALUES (N'd399cb55-c16a-4b0f-aa45-99d23de17ab3', CAST(N'2021-04-21T00:00:00.000' AS DateTime), 5, N'duy4@duy.com', 1)
GO
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'1fc9443d-1858-4a1f-af8a-8438e85cd932', N'update test', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'475fda87-8799-4a91-8d09-3d706fbca2b6', N'test 4', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'52327bd4-6b28-4631-8df9-68788f3ae27b', N'test', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'5c08bf37-8ae0-468a-8ed5-d6a6e2378a52', N'asd', 2, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'7ae27011-f6d4-49ab-b86c-b23746148fe7', N'test', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'7ed9f3ae-de16-4a6b-9a27-78b53656bf68', N'test', 2, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'8137d3c6-7410-4625-a995-a86ff8d1bfaf', N'igsafviub', 2, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'8580d43f-e85a-4953-8ce2-34f77d3be06c', N'search', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'8ea4e9e1-21bc-469c-959c-d19792278460', N'update super test', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'98424dad-e962-47bc-a986-ed2f4ed41a6f', N'test delete', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'9da07f51-c2e3-4231-a9c6-36190217312a', N'update test 3 ', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'a5ab0e45-04f3-418a-9797-1825d77a2993', N'test paging delete', 1, 1, CAST(N'2021-04-19T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'a723562b-9bf0-4718-ad9e-5d0f8788a344', N'test 2', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'ad132af9-0436-4a73-962b-abed53920802', N'test 3 ', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'b172fb7c-1188-4746-91d1-bed4ac10d247', N'asd', 2, 1, CAST(N'2021-04-20T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'bc4babad-2f72-4640-b14e-b9f75f3cef5a', N'test 3', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'd0dd6e61-7a9a-48a2-9a7b-42daaf76f37f', N'ter 333', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'd4395847-6d37-43de-89c1-37af19ad6f2e', N'cau hoi 2', 2, 1, CAST(N'2021-04-20T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'df1617a1-cf3b-4521-9813-69477d90dd08', N'random test', 2, 1, CAST(N'2021-04-20T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'e912827c-3620-43f9-a6d9-b30a785655cd', N'test 3', 1, 1, CAST(N'2021-04-17T00:00:00.000' AS DateTime), NULL)
INSERT [dbo].[tbl_questions] ([id], [question_content], [subject_id], [status], [create_date], [update_date]) VALUES (N'f2e41956-a528-4570-842f-1d4566753424', N'update test 2', 1, 1, CAST(N'2021-04-18T00:00:00.000' AS DateTime), CAST(N'2021-04-19T00:00:00.000' AS DateTime))
GO
SET IDENTITY_INSERT [dbo].[tbl_subjects] ON 

INSERT [dbo].[tbl_subjects] ([id], [subject]) VALUES (1, N'Prj311- Java Desktop')
INSERT [dbo].[tbl_subjects] ([id], [subject]) VALUES (2, N'Prj321- Java Web')
SET IDENTITY_INSERT [dbo].[tbl_subjects] OFF
GO
INSERT [dbo].[tbl_users] ([email], [name], [password], [role], [status]) VALUES (N'admin@admin.com', N'admin     ', N'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', N'ROLE_ADMIN', N'new       ')
INSERT [dbo].[tbl_users] ([email], [name], [password], [role], [status]) VALUES (N'duy@duy.com', N'duy2      ', N'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', N'ROLE_STUDENT', N'new       ')
INSERT [dbo].[tbl_users] ([email], [name], [password], [role], [status]) VALUES (N'duy3@duy.com', N'duy3      ', N'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', N'ROLE_STUDENT', N'new       ')
INSERT [dbo].[tbl_users] ([email], [name], [password], [role], [status]) VALUES (N'duy4@duy.com', N'duy4      ', N'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', N'ROLE_STUDENT', N'new       ')
INSERT [dbo].[tbl_users] ([email], [name], [password], [role], [status]) VALUES (N'new@new.com', N'asd       ', N'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', N'ROLE_STUDENT', N'new       ')
GO
ALTER TABLE [dbo].[tbl_answer_of_user]  WITH CHECK ADD  CONSTRAINT [FK_tbl_answer_of_user_tbl_history_quiz] FOREIGN KEY([history_id])
REFERENCES [dbo].[tbl_history_quiz] ([id])
GO
ALTER TABLE [dbo].[tbl_answer_of_user] CHECK CONSTRAINT [FK_tbl_answer_of_user_tbl_history_quiz]
GO
ALTER TABLE [dbo].[tbl_answers]  WITH CHECK ADD  CONSTRAINT [FK_tbl_answers_tbl_questions] FOREIGN KEY([question_id])
REFERENCES [dbo].[tbl_questions] ([id])
GO
ALTER TABLE [dbo].[tbl_answers] CHECK CONSTRAINT [FK_tbl_answers_tbl_questions]
GO
ALTER TABLE [dbo].[tbl_history_quiz]  WITH CHECK ADD  CONSTRAINT [FK_tbl_history_quiz_tbl_users] FOREIGN KEY([user_email])
REFERENCES [dbo].[tbl_users] ([email])
GO
ALTER TABLE [dbo].[tbl_history_quiz] CHECK CONSTRAINT [FK_tbl_history_quiz_tbl_users]
GO
ALTER TABLE [dbo].[tbl_questions]  WITH CHECK ADD  CONSTRAINT [FK_tbl_questions_tbl_subjects] FOREIGN KEY([subject_id])
REFERENCES [dbo].[tbl_subjects] ([id])
GO
ALTER TABLE [dbo].[tbl_questions] CHECK CONSTRAINT [FK_tbl_questions_tbl_subjects]
GO

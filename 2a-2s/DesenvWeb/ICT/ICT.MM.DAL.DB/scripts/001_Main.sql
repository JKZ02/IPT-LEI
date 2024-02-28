USE [ICTMM]
GO
/****** Object:  Table [dbo].[Devices]    Script Date: 18/05/2022 12:00:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Devices](
	[Id] [uniqueidentifier] NOT NULL,
	[Id_DeviceType] [int] NOT NULL,
	[Name] [varchar](32) NOT NULL,
	[Description] [varchar](64) NOT NULL,
	[ManufacturedDate] [datetime] NOT NULL,
	[LastManteinanceDate] [datetime] NULL,
	[ManteinanceDueDate] [datetime] NULL,
	[ManufacturedBy] [varchar](50) NOT NULL,
	[CreatedBy] [varchar](50) NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[ModifiedBy] [varchar](50) NULL,
	[ModifiedDate] [datetime] NULL,
 CONSTRAINT [PK_Devices] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[DeviceTypes]    Script Date: 18/05/2022 12:00:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[DeviceTypes](
	[Id] [int] NOT NULL,
	[Name] [varchar](32) NOT NULL,
	[Description] [varchar](64) NOT NULL,
 CONSTRAINT [PK_DeviceTypes] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ScenarioDevices]    Script Date: 18/05/2022 12:00:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ScenarioDevices](
	[Id_Scenario] [int] NOT NULL,
	[Id_Device] [uniqueidentifier] NOT NULL,
	[State] [varchar](50) NOT NULL,
 CONSTRAINT [PK_ScenarioDevices] PRIMARY KEY CLUSTERED 
(
	[Id_Scenario] ASC,
	[Id_Device] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Scenarios]    Script Date: 18/05/2022 12:00:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Scenarios](
	[Id] [int] NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[Description] [varchar](50) NOT NULL,
	[IsActive] [bit] NOT NULL,
	[CreatedBy] [varchar](50) NOT NULL,
	[CreatedDate] [datetime] NOT NULL,
	[ModifiedBy] [varchar](50) NULL,
	[ModifiedDate] [datetime] NULL,
 CONSTRAINT [PK_Scenarios] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Devices]  WITH CHECK ADD  CONSTRAINT [FK_Devices_DeviceTypes] FOREIGN KEY([Id_DeviceType])
REFERENCES [dbo].[DeviceTypes] ([Id])
GO
ALTER TABLE [dbo].[Devices] CHECK CONSTRAINT [FK_Devices_DeviceTypes]
GO
ALTER TABLE [dbo].[ScenarioDevices]  WITH CHECK ADD  CONSTRAINT [FK_ScenarioDevices_Devices] FOREIGN KEY([Id_Device])
REFERENCES [dbo].[Devices] ([Id])
GO
ALTER TABLE [dbo].[ScenarioDevices] CHECK CONSTRAINT [FK_ScenarioDevices_Devices]
GO
ALTER TABLE [dbo].[ScenarioDevices]  WITH CHECK ADD  CONSTRAINT [FK_ScenarioDevices_Scenarios] FOREIGN KEY([Id_Scenario])
REFERENCES [dbo].[Scenarios] ([Id])
GO
ALTER TABLE [dbo].[ScenarioDevices] CHECK CONSTRAINT [FK_ScenarioDevices_Scenarios]
GO

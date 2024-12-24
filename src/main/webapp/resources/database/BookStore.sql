//Update Users use uuid
ALTER TABLE [BookStore].[dbo].[Users]
ADD [uuid] NVARCHAR(255) NULL;

UPDATE Users
SET uuid = LOWER(LEFT(SUBSTRING(CONVERT(VARCHAR(255), HASHBYTES('SHA2_256', CAST(id AS NVARCHAR)), 2), 3, 255), 20))
WHERE uuid IS NULL OR uuid = '';

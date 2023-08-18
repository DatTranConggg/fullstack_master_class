-- Insert Location.
INSERT INTO public.location( id,code, name, created_by, created_date)
VALUES 
   (1,'DN', 'Tòa nhà Phi Long, 52 đường Nguyễn Văn Linh, Phường Nam Dương, Quận Hải Châu, Thành phố Đà Nẵng', 'admin', NOW()),
   (2,'HCM', 'Helios Building, Quang Trung Software City Ward, Tân Chánh Hiệp, Quận 12, Thành phố Hồ Chí Minh', 'admin', NOW()),
   (3,'HN', 'Tòa nhà VCCI, số 09, đường Đào Duy Anh, Phường Phương Mai, Quận Đống Đa, Thành phố Hà Nội', 'admin', NOW());
-- Insert Level.
INSERT INTO public.level(id, code, name, description, created_by, created_date)
VALUES
   (1, 'C1', 'Consulting level 1', 'Level C1', 'admin', NOW()),
   (2, 'C2', 'Consulting level 2', 'Level C2', 'admin', NOW()),
   (3, 'SC1', 'Senior consulting 1', 'Level SC1', 'admin', NOW()),
   (4, 'SC2', 'Senior consulting 2', 'Level SC2', 'admin', NOW()),
   (5, 'MCS1', 'Management Consulting Specialist 1', 'Level MCS1', 'admin', NOW()),
   (6, 'MCS2', 'Management Consulting Specialist 2', 'Level MCS2', 'admin', NOW()),
   (7, 'SMCS1', 'Senior Management Consulting Specialist 1', 'Level SMCS1', 'admin', NOW()),
   (8, 'SMCS2', 'Senior Management Consulting Specialist 2', 'Level SMCS2', 'admin', NOW());
-- Insert Branch.
INSERT INTO public.branch(id, code, name, location_id, created_by, created_date)
VALUES
   (1, 'DNB', 'Chi nhánh Đà Nẵng', 1, 'admin', NOW()),
   (2, 'HCMC', 'Chi nhánh Hồ Chí Minh', 2, 'admin', NOW()),
   (3, 'HNB', 'Chi nhánh Hà Nội', 3, 'admin', NOW());


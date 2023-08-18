INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (1, 'STA', 'Solution/Technical Architecture', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (2, 'DDL', 'DEA Delivery Leaders, CEPS', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (3, 'AZC', 'Azure Cloud App Engineering', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (4, 'EME', 'Embedded Engineering', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (5, 'AWC', 'AWS Cloud App Engineering', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (6, 'MBT', 'Mobile Technologies', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (7, 'DAS', 'Data Analytic/Science', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (8, 'FSK', 'Full-Stack', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (9, 'PMO', 'Project Management CoE', 'admin', Now(), 'admin', Now());

INSERT INTO public.center_of_excellence(id, code, name, created_by, created_date, updated_by, updated_date)
VALUES (10, 'SA', 'Solution Architect CoE', 'admin', Now(), 'admin', Now());


INSERT INTO public.business_unit(
	id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (1, 'IoT', 'IoT Deliveries Leader', 'IoT Deliveries Leader', 'Phúc Lê', 'admin', Now(), 'admin', Now());
INSERT INTO public.business_unit(
	id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (2, 'C&D', 'Cloud & Data', 'Cloud & Data', 'Hồng Nguyễn', 'admin', Now(), 'admin', Now());

INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (1, 'UIUX', 'UI UX', 8, 1, 1, 'admin', Now(), 'admin', Now());
INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (2, 'FE', 'FrontEnd', 8, 1, 1, 'admin', Now(), 'admin', Now());
INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (3, 'FSD', 'Fullstack Dotnet', 8, 1, 1, 'admin', Now(), 'admin', Now());
INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (4, 'FSR', 'Fullstack React', 8, 1, 1, 'admin', Now(), 'admin', Now());
INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (5, 'FSA', 'Fullstack Angular', 8, 1, 1, 'admin', Now(), 'admin', Now());
INSERT INTO public.coe_core_team(
	id, code, name, coe_id, sub_leader_id, status, created_by, created_date, updated_by, updated_date)
	VALUES (6, 'BEP', 'BackEnd Python', 8, 1, 1, 'admin', Now(), 'admin', Now());

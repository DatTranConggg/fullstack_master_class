INSERT INTO public.practice(
	id, business_unit_id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (1, 1, 'EMB', 'EMB', 'DS IOT Embedded Systems', 'Lan Thai Do', 'admin', Now(), 'admin', Now());
INSERT INTO public.practice(
	id, business_unit_id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (2, 1, 'IoM', 'IoM', 'IoM i4.0', 'Lan Thai Do', 'admin', Now(), 'admin', Now());
INSERT INTO public.practice(
	id, business_unit_id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (3, 1, 'ALM', 'ALM', 'Asset Lifecycle Management', 'Lan Thai Do', 'admin', Now(), 'admin', Now());
INSERT INTO public.practice(
	id, business_unit_id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (4, 2, 'DS', 'DS DM App Engineering', 'DS DM App Engineering', 'Lan Thai Do', 'admin', Now(), 'admin', Now());
INSERT INTO public.practice(
	id, business_unit_id, code, name, description, manager, created_by, created_date, updated_by, updated_date)
	VALUES (5, 2, 'CMS', 'Content Management System', 'Content Management System', 'Lan Thai Do', 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (1, 'IT App', 'IT Application', 'Information Technology Application', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (2, 'Retail', 'Retail', 'Retail Industry', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (3, 'Finance', 'Finance', 'Finance Industry', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (4, 'Insurance', 'Insurance', 'Insurance Industry', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (5, 'Video Analysis', 'Video Analysis', 'Video Analysis Domain', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (6, 'Infrastructure', 'Infrastructure', 'Infrastructure Domain', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (7, 'Printing', 'Printing', 'Printing Industry', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (8, 'Security Network', 'Security Network', 'Security Network Domain', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (9, 'Healthcare', 'Healthcare', 'Healthcare Industry', 4, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (10, 'Banking, Finance', 'Banking, Finance', 'Banking, Finance Industry', 5, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (11, 'Hotel & Resort', 'Hotel & Resort', 'Hotel & Resort Industry', 5, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (12, 'CMS', 'CMS', 'Content Management System', 5, 'admin', Now(), 'admin', Now());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (13, 'Transportation', 'Transportation', 'Transportation Industry', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (14, 'Database', 'Database', 'Database Management', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (15, 'Google Cloud', 'Google Cloud', 'Google Cloud Platform', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (16, 'Oncall Product', 'Oncall Product', 'Oncall Product Development', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (17, 'Mobile', 'Mobile', 'Mobile Application Development', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (18, 'Application', 'Application', 'Application Development', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (19, 'Web application', 'Web application', 'Web Application Development', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (20, 'Semiconductors (SEMI)', 'Semiconductors (SEMI)', 'Semiconductor Industry', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (21, 'Automotive', 'Automotive', 'Automotive Industry', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (22, 'AI, Web App', 'AI, Web App', 'Artificial Intelligence Web Application', 2, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (23, 'Transportation - Rail', 'Transportation - Rail', 'Rail Transportation Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (24, 'Train', 'Train', 'Train Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (25, 'Tool', 'Tool', 'Tool Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (26, 'Industry', 'Industry', 'Industry Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (27, 'Pharmacy', 'Pharmacy', 'Pharmacy Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (28, 'Web application / Mobile application', 'Web application / Mobile application', 'Web and Mobile Application Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (29, 'Firm Law', 'Firm Law', 'Law Firm', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (30, 'L&D', 'L&D', 'Learning & Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (31, 'Railway', 'Railway', 'Railway Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (32, 'MFP - Multi Functional Peripheral', 'MFP - Multi Functional Peripheral', 'Multi Functional Peripheral Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (33, 'Smart factory', 'Smart factory', 'Smart Factory Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (34, 'Smart agriculture', 'Smart agriculture', 'Smart Agriculture Development', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (35, 'Communication & Entertainment', 'Communication & Entertainment', 'Communication and Entertainment Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (36, 'Medical', 'Medical', 'Medical Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (37, 'Mobility', 'Mobility', 'Mobility Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.business_domain(id, code, name, description, practice_id, created_by, created_date, updated_by, updated_date)
VALUES (38, 'CMT', 'CMT', 'CMT Industry', 1, 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (1, 'Outstanding', 'Outstanding', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (2, 'Exceeds Expectation', 'Exceeds Expectation', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (3, 'Meets Expectation 3', 'Meets Expectation 3', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (4, 'Meets Expectation 2', 'Meets Expectation 2', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (5, 'Meets Expectation 1', 'Meets Expectation 1', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (6, 'Need support', 'Need support', 'admin', NOW(), 'admin', NOW());

INSERT INTO public.evaluation_level(
	id, code, description, created_by, created_date, updated_by, updated_date)
	VALUES (7, 'Inconsistent', 'Inconsistent', 'admin', NOW(), 'admin', NOW());

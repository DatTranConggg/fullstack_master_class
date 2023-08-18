-- FUNCTION: getQuantityOfSkillByConditions(integer, integer, integer, character varying)
-- Created by nnien
CREATE OR REPLACE FUNCTION getQuantityOfSkillByConditions(
  branchId INTEGER,
  groupId INTEGER,
  teamId INTEGER,
  skillIds character varying
)
RETURNS TABLE (labels VARCHAR(250), skillName VARCHAR(250), total BIGINT)
AS $$
BEGIN
    RETURN QUERY
    SELECT 
        CASE
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
            WHEN branchId IS NOT NULL AND groupId IS NULL AND teamId IS NULL THEN coe.name
            WHEN branchId IS NULL AND groupId IS NULL AND teamId IS NULL THEN br.name    
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
        END AS labelName,
        ss.name AS skillName,
        COUNT(esk.employee_id) AS total
    FROM employee e
    JOIN employee_skill esk ON e.id = esk.employee_id
    JOIN skill_set ss ON esk.skill_set_id = ss.id 
    JOIN branch br ON e.branch_id = br.id
    JOIN coe_core_team team ON e.coe_core_team_id = team.id
    JOIN center_of_excellence coe ON team.coe_id = coe.id
    JOIN employee_status emsta ON emsta.employee_id = e.id
    WHERE (br.id = branchId OR branchId IS NULL)
        AND (coe.id = groupId OR groupId IS NULL)
        AND (team.id = teamId OR teamId IS NULL)
        AND (ss.id::TEXT IN (SELECT unnest(string_to_array(skillIds, ','))) OR skillIds IS NULL)
        AND (emsta.id IN (
            SELECT es.id 
            FROM employee_status es 
            JOIN (
                SELECT DISTINCT es.employee_id, MAX(es.status_date) as latestDate 
                FROM employee_status es 
                JOIN employee e ON es.employee_id = e.id
                GROUP BY es.employee_id
            ) newEmpl ON newEmpl.latestDate = es.status_date AND es.employee_id = newEmpl.employee_id
            WHERE es.status <> 0
        ))
    GROUP BY labelName, skillName
    UNION ALL
    SELECT CASE
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
            WHEN branchId IS NOT NULL AND groupId IS NULL AND teamId IS NULL THEN coe.name
            WHEN branchId IS NULL AND groupId IS NULL AND teamId IS NULL THEN br.name    
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
        END AS labelName,
        'Others' as skillName, 
        COUNT(esk.employee_id) as total
    FROM employee e
    JOIN employee_skill esk ON e.id = esk.employee_id
    JOIN skill_set ss ON esk.skill_set_id = ss.id 
    JOIN branch br ON e.branch_id = br.id
    JOIN coe_core_team team ON e.coe_core_team_id = team.id
    JOIN center_of_excellence coe ON team.coe_id = coe.id
    JOIN employee_status emsta ON emsta.employee_id = e.id
    WHERE (br.id = branchId OR branchId IS NULL)
        AND (coe.id = groupId OR groupId IS NULL)
        AND (team.id = teamId OR teamId IS NULL)
        AND (ss.id::TEXT NOT IN (SELECT unnest(string_to_array(skillIds, ','))) OR skillIds IS NULL)
        AND (emsta.id IN (
            SELECT es.id 
            FROM employee_status es 
            JOIN (
                SELECT DISTINCT es.employee_id, MAX(es.status_date) as latestDate 
                FROM employee_status es 
                JOIN employee e ON es.employee_id = e.id
                GROUP BY es.employee_id
            ) newEmpls ON newEmpls.latestDate = es.status_date AND es.employee_id = newEmpls.employee_id
            WHERE es.status <> 0
        ))
    GROUP BY labelName, skillName;
END;
$$ LANGUAGE plpgsql;


-- FUNCTION: public.get_utilization_pie_chart(integer, integer, integer, date, date)

-- DROP FUNCTION IF EXISTS public.get_utilization_pie_chart(integer, integer, integer, date, date);
-- Created by Loi Ta
CREATE OR REPLACE FUNCTION public.get_utilization_pie_chart(
	branchid integer DEFAULT NULL::integer,
	coeid integer DEFAULT NULL::integer,
	coecoreteamid integer DEFAULT NULL::integer,
	fromdate date DEFAULT NULL::date,
	todate date DEFAULT NULL::date)
    RETURNS TABLE(labels text, amount bigint) 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
 
BEGIN
    RETURN QUERY SELECT 
        CASE 
            WHEN EU.BILLABLE >= 80  THEN '> 80%'
            WHEN EU.BILLABLE >= 20 AND EU.BILLABLE < 40
                THEN '> 20%' 
            WHEN EU.BILLABLE >= 40 AND EU.BILLABLE < 60
                THEN '> 40%' 
            WHEN EU.BILLABLE >= 60 AND EU.BILLABLE < 80
                THEN '> 60%' 
            ELSE '< 20%' 
        END AS LABELS, COUNT(DISTINCT EU.ID) AS AMOUNT 
    FROM (
		SELECT  EU.employee_id AS ID, SUM(EU.billable) / COUNT(EU.employee_id) AS BILLABLE, count(EU.employee_id) 
			FROM PUBLIC.EMPLOYEE_UTILIZATION EU 
			INNER JOIN PUBLIC.EMPLOYEE E ON EU.EMPLOYEE_ID = E.ID 
		        AND (E.BRANCH_ID = COALESCE(branchId, E.BRANCH_ID) OR COALESCE(branchId, 0) = null) 
		        AND (E.COE_CORE_TEAM_ID = COALESCE(coeCoreTeamId, E.COE_CORE_TEAM_ID) OR COALESCE(coeCoreTeamId, 0) = null)  
			INNER JOIN PUBLIC.coe_utilization CU ON CU.id = EU.coe_utilization_id 
				AND (CU.start_date BETWEEN COALESCE(fromdate, CU.start_date) AND COALESCE(todate, CU.start_date))
			INNER JOIN PUBLIC.COE_CORE_TEAM CT ON E.COE_CORE_TEAM_ID = CT.ID 
			INNER JOIN PUBLIC.CENTER_OF_EXCELLENCE COE ON CT.COE_ID = COE.ID 
				INNER JOIN (
				select ut.coe_utilization_id as coe_util_id,employee_id,max(created_date) as lastest_date from employee_utilization ut 
				group by ut.coe_utilization_id, employee_id
				order by employee_id) UTN on UTN.employee_id = E.id 
				and UTN.coe_util_id = EU.coe_utilization_id 
			    and EU.created_date = UTN.lastest_date
		    WHERE COALESCE(coeId,  0) = null OR coe.id = COALESCE(coeId, coe.id)
			GROUP BY eu.employee_id HAVING COUNT(*) > 0) EU
    GROUP BY LABELS;
END;  
$BODY$;


-- FUNCTION: public.piechartlevel(integer, integer, integer)
-- Created by Tai Tan Nguyen
CREATE OR REPLACE FUNCTION piechartlevel(
    IN p_branch_id INTEGER,
    IN p_core_team_id INTEGER,
    IN p_coe_id INTEGER
)
RETURNS TABLE(
    name CHARACTER VARYING(250),
    total_employees BIGINT
) AS $$
   BEGIN
    RETURN QUERY
    SELECT lv.name, COUNT(DISTINCT emp.id) AS total_employees
    FROM (
    SELECT DISTINCT ON (employee_id) employee_id, level_id
    FROM employee_level
    ORDER BY employee_id, level_date DESC
    ) emplv
    INNER JOIN level lv ON emplv.level_id = lv.id
    INNER JOIN employee emp ON emplv.employee_id = emp.id
    LEFT JOIN coe_core_team coeT ON emp.coe_core_team_id = coeT.id
    LEFT JOIN center_of_excellence coe ON coeT.coe_id = coe.id
    WHERE (emp.branch_id = COALESCE(p_branch_id, emp.branch_id) OR COALESCE(p_branch_id, null) = null)
    AND (emp.coe_core_team_id = COALESCE(p_core_team_id, emp.coe_core_team_id) OR COALESCE(p_core_team_id, null) = null)
    AND (coeT.coe_id = COALESCE(p_coe_id, coeT.coe_id) OR COALESCE(p_coe_id, null) = null)
    GROUP BY lv.name
    ORDER BY total_employees DESC;
   END;
$$ LANGUAGE plpgsql;


-- FUNCTION: public.get_skill_pie_chart(integer, integer, integer, text)
-- Created by Plot Dieu
CREATE OR REPLACE FUNCTION public.get_skill_pie_chart(
	branchid integer DEFAULT NULL::integer,
	coecoreteamid integer DEFAULT NULL::integer,
	coreid integer DEFAULT NULL::integer,
	topskill text DEFAULT NULL::text)
    RETURNS TABLE(name character varying, total_employees bigint) 
    LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
	SELECT
    ss.name AS name,
    COUNT(DISTINCT em.id) AS total_employees
FROM employee em
    INNER JOIN (SELECT employee_id, MAX(status_date) AS latest_status_date
        FROM 
            employee_status 
        GROUP BY 
            employee_id
    ) AS latest_status ON latest_status.employee_id = em.id
    INNER JOIN employee_status ems ON ems.employee_id = latest_status.employee_id 
	AND ems.status_date = latest_status.latest_status_date
    INNER JOIN employee_skill esk ON em.id = esk.employee_id
    INNER JOIN skill_set ss ON esk.skill_set_id = ss.id 
    INNER JOIN branch br ON em.branch_id = br.id 
    INNER JOIN coe_core_team ct ON em.coe_core_team_id = ct.id 
    INNER JOIN center_of_excellence coe ON ct.coe_id = coe.id
WHERE 
    (branchid IS NULL OR br.id = branchid)
    AND (coecoreteamid IS NULL OR ct.id = coecoreteamid)
    AND (coreid IS NULL OR coe.id = coreid)
    AND esk.skill_set_id IN (SELECT UNNEST(string_to_array(topskill, ','))::integer)
    AND ems.status <>0
GROUP BY
    ss.name

UNION ALL

SELECT
    'Others' AS name,
    COUNT(DISTINCT skill.id) AS total_employees
FROM
    public.employee_skill skill
WHERE
    skill.skill_set_id NOT IN (SELECT UNNEST(string_to_array(topskill, ','))::integer)
    AND skill.employee_id IN (
        SELECT
            em.id
        FROM
            employee em
            INNER JOIN (SELECT employee_id, MAX(status_date) AS latest_status_date
                FROM 
                    employee_status 
                GROUP BY 
                    employee_id
            ) AS latest_status ON latest_status.employee_id = em.id
            INNER JOIN employee_status ems ON ems.employee_id = latest_status.employee_id 
		    AND ems.status_date = latest_status.latest_status_date
            INNER JOIN branch br ON em.branch_id = br.id 
            INNER JOIN coe_core_team ct ON em.coe_core_team_id = ct.id 
            INNER JOIN center_of_excellence coe ON ct.coe_id = coe.id 
        WHERE 
            (branchid IS NULL OR br.id = branchid)
            AND (coecoreteamid IS NULL OR ct.id = coecoreteamid)
            AND (coreid IS NULL OR coe.id = coreid)
            AND ems.status <>0
    );
END;
$BODY$;

-- FUNCTION: public.getQuantityOfLevelForBarChart(integer, integer, integer)
-- Created by Tinh Tri Bui
CREATE OR REPLACE FUNCTION getQuantityOfLevelForBarChart(
    branchId INTEGER,
    groupId INTEGER,
    teamId INTEGER
) RETURNS TABLE (labels VARCHAR(250), levelName VARCHAR(250), total BIGINT)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        CASE
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NOT NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
            WHEN branchId IS NOT NULL AND groupId IS NULL AND teamId IS NULL THEN coe.name
            WHEN branchId IS NULL AND groupId IS NULL AND teamId IS NULL THEN br.name
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NOT NULL THEN e.name
            WHEN branchId IS NULL AND groupId IS NOT NULL AND teamId IS NULL THEN team.name
        END AS labelName,
        lv.name AS levelName,
        COUNT(elv.employee_id) AS total
    FROM employee e
    JOIN (
        SELECT DISTINCT ON (employee_id) employee_id, MAX(level_date) AS latest_date
        FROM employee_level
        GROUP BY employee_id
        ORDER BY employee_id, MAX(level_date) DESC
    ) el ON e.id = el.employee_id
    JOIN employee_level elv ON el.employee_id = elv.employee_id AND el.latest_date = elv.level_date
    JOIN level lv ON elv.level_id = lv.id
    JOIN branch br ON e.branch_id = br.id
    JOIN coe_core_team team ON e.coe_core_team_id = team.id
    JOIN center_of_excellence coe ON team.coe_id = coe.id
    WHERE (br.id = branchId OR branchId IS NULL)
        AND (coe.id = groupId OR groupId IS NULL)
        AND (team.id = teamId OR teamId IS NULL)
    GROUP BY levelName, labelName
    ORDER BY total DESC;
END;
$$ LANGUAGE plpgsql;



-- FUNCTION: public.getQuantityEmployeeOfLevel(integer, integer, integer)
-- Created by Hung Chan Tran
CREATE OR REPLACE FUNCTION getQuantityEmployeeOfLevel(
    branchId INTEGER,
    groupId INTEGER,
    teamId INTEGER
) RETURNS TABLE (levelName VARCHAR(250), total BIGINT)
AS $$
BEGIN
    RETURN QUERY
    SELECT
        lv.name AS levelName,
        COUNT(el.employee_id) AS total
    FROM employee e
    JOIN (
        SELECT employee_id, MAX(level_date) AS latest_date, level_id
        FROM employee_level
        GROUP BY employee_id, level_id
    ) el ON e.id = el.employee_id
    JOIN level lv ON el.level_id = lv.id
    JOIN branch br ON e.branch_id = br.id
    JOIN coe_core_team team ON e.coe_core_team_id = team.id
    JOIN center_of_excellence coe ON team.coe_id = coe.id
    WHERE (br.id = branchId OR branchId IS NULL)
        AND (coe.id = groupId OR groupId IS NULL)
        AND (team.id = teamId OR teamId IS NULL)
    GROUP BY levelName
    ORDER BY total DESC;
END;
$$ LANGUAGE plpgsql;
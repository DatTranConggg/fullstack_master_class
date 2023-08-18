-- FUNCTION: getEemployeeUtilizationFree(VARCHAR(50), INTEGER, VARCHAR(100), VARCHAR(50), VARCHAR(250), VARCHAR(250), VARCHAR(250), INTEGER)
-- Created by tquangpham
CREATE OR REPLACE FUNCTION getEmployeeUtilizationFree(
billableThreshold double precision)
RETURNS TABLE (
    hccId VARCHAR(50),
    id INTEGER,
    employeeName VARCHAR(100),
    email VARCHAR(50),
    branchName VARCHAR(250),
    businessName VARCHAR(250),
    teamName VARCHAR(250),
    daysFree INTEGER
) AS $$
BEGIN
    RETURN QUERY
    SELECT DISTINCT ON (e.hcc_id)
        e.hcc_id,
        e.id,
        e.name,
        e.email,
        b.name,
        bu.name,
        t.name,
        CAST(DATE_PART('day', now() - eu.created_date) AS INTEGER) AS daysFree
    FROM employee e
    JOIN employee_utilization eu ON eu.employee_id = e.id
    JOIN (
        SELECT ut.employee_id, max(ut.created_date) as lastest_date
        FROM employee_utilization ut
        GROUP BY employee_id
    ) eut ON eut.employee_id = e.id AND eu.created_date = eut.lastest_date
    JOIN branch b ON b.id = e.branch_id
    JOIN business_unit bu ON bu.id = e.business_unit_id
    JOIN coe_core_team t ON t.id = e.coe_core_team_id
    WHERE DATE_PART('day', now() - eu.created_date) > 0
        AND (eu.billable < billableThreshold OR billableThreshold IS NULL)
        AND e.id IN (
            SELECT es.employee_id
            FROM employee_status es
            WHERE es.status = 1
                AND es.employee_id = e.id
            GROUP BY es.employee_id
            HAVING MAX(es.status_date) = (
                SELECT MAX(es2.status_date)
                FROM employee_status es2
                WHERE es2.employee_id = es.employee_id
            )
        )
    UNION ALL
    SELECT DISTINCT ON (e.hcc_id)
        e.hcc_id,
        e.id,
        e.name,
        e.email,
        b.name,
        bu.name,
        t.name,
        CAST(DATE_PART('day', now() - e.created_date) AS INTEGER) AS daysFree
    FROM employee e
    JOIN branch b ON b.id = e.branch_id
    JOIN business_unit bu ON bu.id = e.business_unit_id
    JOIN coe_core_team t ON t.id = e.coe_core_team_id
    WHERE DATE_PART('day', now() - e.created_date) > 0
        AND NOT EXISTS (
            SELECT 1
            FROM employee_utilization eu
            WHERE eu.employee_id = e.id
        )
        AND e.id IN (
            SELECT es.employee_id
            FROM employee_status es
            WHERE es.status = 1
                AND es.employee_id = e.id
            GROUP BY es.employee_id
            HAVING MAX(es.status_date) = (
                SELECT MAX(es2.status_date)
                FROM employee_status es2
                WHERE es2.employee_id = es.employee_id
            )
        );
END;
$$ LANGUAGE plpgsql;
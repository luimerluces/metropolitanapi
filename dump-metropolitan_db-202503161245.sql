--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.0

-- Started on 2025-03-16 12:45:38

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3430 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 253 (class 1255 OID 49351)
-- Name: activity_delete(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.activity_delete(id_activity integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE activity SET status = false WHERE id=id_activity; 
    RETURN 'record deleted successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name activity.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error : ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.activity_delete(id_activity integer) OWNER TO admin;

--
-- TOC entry 249 (class 1255 OID 32856)
-- Name: activity_insert(character varying, date, integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.activity_insert(name_activity character varying, date_activity date, id_spaces integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
    INSERT INTO activity (name,scheduled,spaces_id) VALUES (name_activity,date_activity,id_spaces);
    RETURN 'Successfully inserted activity';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name activity already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the spaces: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.activity_insert(name_activity character varying, date_activity date, id_spaces integer) OWNER TO admin;

--
-- TOC entry 250 (class 1255 OID 41006)
-- Name: activity_update(integer, character varying, date, integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.activity_update(id_activity integer, name_activity character varying, scheduled_date date, spaces integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE activity SET name = name_activity , scheduled = scheduled_date , spaces_id= spaces WHERE id = id_activity; 
    RETURN 'Updated successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name activity already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the activity: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.activity_update(id_activity integer, name_activity character varying, scheduled_date date, spaces integer) OWNER TO admin;

--
-- TOC entry 232 (class 1255 OID 32852)
-- Name: get_activity(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_activity() RETURNS TABLE(id integer, name character varying, scheduled date, space character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY SELECT
        a.id,
        a.name,
        a.scheduled,
        s."name" AS space
    FROM
        activity a
    JOIN spaces s ON a.spaces_id = s.id
	where
		a.status=true;
END; $$;


ALTER FUNCTION public.get_activity() OWNER TO admin;

--
-- TOC entry 247 (class 1255 OID 32854)
-- Name: get_activity_id(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_activity_id(id_activity integer) RETURNS TABLE(id integer, name character varying, scheduled date, space character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY SELECT
        a.id,
        a.name,
        a.scheduled,
        s."name" AS space
    FROM
        activity a
    JOIN spaces s ON a.spaces_id = s.id
	where
		a.status=true
    and
        a.id=id_activity;
END; $$;


ALTER FUNCTION public.get_activity_id(id_activity integer) OWNER TO admin;

--
-- TOC entry 255 (class 1255 OID 49355)
-- Name: get_member(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_member() RETURNS TABLE(id_client integer, name_client character varying, dni_client character varying, city_client character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY select  
		m.id as client_id,
		m.name as name_member,
		m.dni as dni,
		m.city as city
		from 
			"member" m
	    where 
			m.status=true
		order by m.id asc;
END; $$;


ALTER FUNCTION public.get_member() OWNER TO admin;

--
-- TOC entry 251 (class 1255 OID 57391)
-- Name: get_member(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_member(id_member integer) RETURNS TABLE(id_client integer, name_client character varying, dni_client character varying, city_client character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY select  
		m.id as client_id,
		m.name as name_member,
		m.dni as dni,
		m.city as city
		from 
			"member" m
	    where 
			m.status=true
		order by m.id asc;
END; $$;


ALTER FUNCTION public.get_member(id_member integer) OWNER TO admin;

--
-- TOC entry 254 (class 1255 OID 49354)
-- Name: get_member_activity(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_member_activity(num_client integer) RETURNS TABLE(state_client character varying, activity_client character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY select  
		a.name as state,
		ac.name as actividad
		from 
			"member" m,
			jsonb_array_elements(m.calendario)as e
		inner join 
			states_schedule  a on (e->>'state')::int = a.id
		inner join 
			activity ac on (e->>'actividad_id')::int = ac.id
        where 
			m.status=true
		and  
			m.id=num_client;
END; $$;


ALTER FUNCTION public.get_member_activity(num_client integer) OWNER TO admin;

--
-- TOC entry 252 (class 1255 OID 57392)
-- Name: get_member_id(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_member_id(id_member integer) RETURNS TABLE(id_client integer, name_client character varying, dni_client character varying, city_client character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY select  
		m.id as client_id,
		m.name as name_member,
		m.dni as dni,
		m.city as city
		from 
			"member" m
	    where 
			m.status=true
		and
			m.id=id_member
		order by m.id asc;
END; $$;


ALTER FUNCTION public.get_member_id(id_member integer) OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 49241)
-- Name: spaces; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.spaces (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL,
    status boolean DEFAULT true
);


ALTER TABLE public.spaces OWNER TO admin;

--
-- TOC entry 225 (class 1255 OID 49247)
-- Name: get_spaces(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_spaces() RETURNS SETOF public.spaces
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT *
    FROM spaces where status=true;
END;
$$;


ALTER FUNCTION public.get_spaces() OWNER TO admin;

--
-- TOC entry 226 (class 1255 OID 49248)
-- Name: get_spaces_id(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_spaces_id(id_spaces integer) RETURNS SETOF public.spaces
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT *
    	FROM spaces 
	where 
		id=id_spaces
	and
		status=true;
END;
$$;


ALTER FUNCTION public.get_spaces_id(id_spaces integer) OWNER TO admin;

--
-- TOC entry 218 (class 1259 OID 49249)
-- Name: states_schedule; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.states_schedule (
    id integer NOT NULL,
    name character varying NOT NULL,
    status boolean DEFAULT true
);


ALTER TABLE public.states_schedule OWNER TO admin;

--
-- TOC entry 227 (class 1255 OID 49255)
-- Name: get_states_schedule(); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_states_schedule() RETURNS SETOF public.states_schedule
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT *
    FROM states_schedule where status=true;
END;
$$;


ALTER FUNCTION public.get_states_schedule() OWNER TO admin;

--
-- TOC entry 228 (class 1255 OID 49256)
-- Name: get_states_schedule_id(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.get_states_schedule_id(id_states_schedule integer) RETURNS SETOF public.states_schedule
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT *
    	FROM states_schedule 
	where 
		id=id_states_schedule
	and
		status=true;
END;
$$;


ALTER FUNCTION public.get_states_schedule_id(id_states_schedule integer) OWNER TO admin;

--
-- TOC entry 229 (class 1255 OID 49350)
-- Name: member_delete(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.member_delete(id_member integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	delete from member WHERE id=id_member; 
    RETURN 'record deleted successfully';
END;
$$;


ALTER FUNCTION public.member_delete(id_member integer) OWNER TO admin;

--
-- TOC entry 256 (class 1255 OID 65585)
-- Name: member_insert(character varying, character varying, character varying, jsonb, integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.member_insert(p_name character varying, p_dni character varying, p_city character varying, p_calendario jsonb, id_member integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
    INSERT INTO public."member" ("name", dni, city, calendario,id)VALUES (p_name, p_dni, p_city, p_calendario,id_member);
    RETURN 'Successfully inserted status';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The member already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the member: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.member_insert(p_name character varying, p_dni character varying, p_city character varying, p_calendario jsonb, id_member integer) OWNER TO admin;

--
-- TOC entry 248 (class 1255 OID 32821)
-- Name: spaces_delete(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.spaces_delete(id_spaces integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE spaces SET status = false WHERE id=id_spaces; 
    RETURN 'record deleted successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name spaces already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the spaces: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.spaces_delete(id_spaces integer) OWNER TO admin;

--
-- TOC entry 245 (class 1255 OID 32819)
-- Name: spaces_insert(character varying, character varying); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.spaces_insert(name_spaces character varying, despcrition_spaces character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
    INSERT INTO spaces (name,description) VALUES (name_spaces,despcrition_spaces);
    RETURN 'Successfully inserted status';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name spaces already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the spaces: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.spaces_insert(name_spaces character varying, despcrition_spaces character varying) OWNER TO admin;

--
-- TOC entry 246 (class 1255 OID 32820)
-- Name: spaces_update(integer, character varying, character varying); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.spaces_update(id_spaces integer, name_spaces character varying, description_spaces character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE spaces SET name = name_spaces , description = description_spaces WHERE id = id_spaces; 
    RETURN 'Updated successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name spaces already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the states_schedule: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.spaces_update(id_spaces integer, name_spaces character varying, description_spaces character varying) OWNER TO admin;

--
-- TOC entry 243 (class 1255 OID 32815)
-- Name: states_schedule_delete(integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.states_schedule_delete(id_states integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE states_schedule	SET status = false WHERE id=id_states; 
    RETURN 'record deleted successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name states_schedule already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the states_schedule: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.states_schedule_delete(id_states integer) OWNER TO admin;

--
-- TOC entry 230 (class 1255 OID 24625)
-- Name: states_schedule_insert(character varying); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.states_schedule_insert(name_states character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
    INSERT INTO states_schedule (name) VALUES (name_states);
    RETURN 'Successfully inserted status';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name states_schedule already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the states_schedule: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.states_schedule_insert(name_states character varying) OWNER TO admin;

--
-- TOC entry 231 (class 1255 OID 24626)
-- Name: states_schedule_update(character varying, integer); Type: FUNCTION; Schema: public; Owner: admin
--

CREATE FUNCTION public.states_schedule_update(name_states character varying, id_states integer) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
    error_message VARCHAR;
BEGIN
	UPDATE states_schedule	SET name = name_states WHERE id=id_states; 
    RETURN 'Updated successfully';
EXCEPTION
    WHEN unique_violation THEN
        error_message := 'Error: The name states_schedule already exists.';
        RETURN error_message;
    WHEN others THEN
        error_message := 'Unknown error while inserting the states_schedule: ' || SQLERRM;
        RETURN error_message;
END;
$$;


ALTER FUNCTION public.states_schedule_update(name_states character varying, id_states integer) OWNER TO admin;

--
-- TOC entry 219 (class 1259 OID 49257)
-- Name: activity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.activity (
    id integer NOT NULL,
    name character varying NOT NULL,
    scheduled date NOT NULL,
    spaces_id integer NOT NULL,
    status boolean DEFAULT true
);


ALTER TABLE public.activity OWNER TO admin;

--
-- TOC entry 220 (class 1259 OID 49263)
-- Name: activity_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.activity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.activity_id_seq OWNER TO admin;

--
-- TOC entry 3431 (class 0 OID 0)
-- Dependencies: 220
-- Name: activity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.activity_id_seq OWNED BY public.activity.id;


--
-- TOC entry 221 (class 1259 OID 49264)
-- Name: member; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.member (
    name character varying NOT NULL,
    dni character varying NOT NULL,
    id integer NOT NULL,
    city character varying NOT NULL,
    calendario jsonb NOT NULL,
    status boolean DEFAULT true
);


ALTER TABLE public.member OWNER TO admin;

--
-- TOC entry 222 (class 1259 OID 49269)
-- Name: member_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.member_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.member_id_seq OWNER TO admin;

--
-- TOC entry 3432 (class 0 OID 0)
-- Dependencies: 222
-- Name: member_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.member_id_seq OWNED BY public.member.id;


--
-- TOC entry 223 (class 1259 OID 49270)
-- Name: spaces_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.spaces_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.spaces_id_seq OWNER TO admin;

--
-- TOC entry 3433 (class 0 OID 0)
-- Dependencies: 223
-- Name: spaces_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.spaces_id_seq OWNED BY public.spaces.id;


--
-- TOC entry 224 (class 1259 OID 49271)
-- Name: states_schedule_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.states_schedule_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.states_schedule_id_seq OWNER TO admin;

--
-- TOC entry 3434 (class 0 OID 0)
-- Dependencies: 224
-- Name: states_schedule_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE public.states_schedule_id_seq OWNED BY public.states_schedule.id;


--
-- TOC entry 3250 (class 2604 OID 49272)
-- Name: activity id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.activity ALTER COLUMN id SET DEFAULT nextval('public.activity_id_seq'::regclass);


--
-- TOC entry 3252 (class 2604 OID 49273)
-- Name: member id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.member ALTER COLUMN id SET DEFAULT nextval('public.member_id_seq'::regclass);


--
-- TOC entry 3246 (class 2604 OID 49274)
-- Name: spaces id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces ALTER COLUMN id SET DEFAULT nextval('public.spaces_id_seq'::regclass);


--
-- TOC entry 3248 (class 2604 OID 49275)
-- Name: states_schedule id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.states_schedule ALTER COLUMN id SET DEFAULT nextval('public.states_schedule_id_seq'::regclass);


--
-- TOC entry 3419 (class 0 OID 49257)
-- Dependencies: 219
-- Data for Name: activity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.activity (id, name, scheduled, spaces_id, status) FROM stdin;
3	spining	2025-01-07	3	t
4	spining	2025-02-07	3	t
1	padel infanil	2025-01-07	3	t
5	Prueba	2025-03-14	2	t
8	AAAAA	2025-03-13	2	t
9	AaaaaaAAAA	2025-03-13	2	t
2	Derek	2025-03-13	3	t
10	Derekkkkkkk	2025-03-13	3	f
7	Test Ultimo 3	2025-05-12	3	f
\.


--
-- TOC entry 3421 (class 0 OID 49264)
-- Dependencies: 221
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.member (name, dni, id, city, calendario, status) FROM stdin;
Luis	3264759	2	Valencia	[{"state": 2, "actividad_id": 2}, {"state": 1, "actividad_id": 3}]	t
Oscar	2456715	1	Madrid	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Luimer	13760252	3	Caracas	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Nombre del Miembro	12345678	4	Ciudad del Miembro	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Nombre del Miembro3	12345679	5	Ciudad del Miembro	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Nombre del Miembro3	12345659	6	Ciudad del Miembro	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Nombre del Miembro3	12347659	7	Ciudad del Miembro	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Wladimir	13760212	9	Caracas	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Antonio	2456710	8	Guarena	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Posada	2456770	10	New Yrok	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Roger	2455770	11	New Yrok	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Mike	2775770	12	New Yrok	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]	t
Soriano	2770770	13	New Yrok	[{"state": 2, "actividad_id": 2}, {"state": 3, "actividad_id": 2}]	t
\.


--
-- TOC entry 3417 (class 0 OID 49241)
-- Dependencies: 217
-- Data for Name: spaces; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.spaces (id, name, description, status) FROM stdin;
5	cycling aula para clases de cycling/spining	usar en clases infantiles	t
6	prueba	prueba	t
7	prueba22	prueba22	t
2	prueba88888	prueba88888	t
3	prueba33333333	prueba33333333	t
\.


--
-- TOC entry 3418 (class 0 OID 49249)
-- Dependencies: 218
-- Data for Name: states_schedule; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.states_schedule (id, name, status) FROM stdin;
4	ausente	t
5	test	t
6	Test1	t
7	Test11	t
8	Test43	t
9	Test443	t
10	Test4439	t
1	reserva	f
2	cancelar	f
11	Test4439111	t
12	Test Ultimo	t
3	Test Ultimo 3	f
\.


--
-- TOC entry 3435 (class 0 OID 0)
-- Dependencies: 220
-- Name: activity_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.activity_id_seq', 10, true);


--
-- TOC entry 3436 (class 0 OID 0)
-- Dependencies: 222
-- Name: member_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.member_id_seq', 2, true);


--
-- TOC entry 3437 (class 0 OID 0)
-- Dependencies: 223
-- Name: spaces_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.spaces_id_seq', 7, true);


--
-- TOC entry 3438 (class 0 OID 0)
-- Dependencies: 224
-- Name: states_schedule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.states_schedule_id_seq', 12, true);


--
-- TOC entry 3266 (class 2606 OID 49277)
-- Name: activity activity_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_pk PRIMARY KEY (id);


--
-- TOC entry 3255 (class 2606 OID 49279)
-- Name: spaces spaces_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_pk PRIMARY KEY (id);


--
-- TOC entry 3257 (class 2606 OID 49281)
-- Name: spaces spaces_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_unique UNIQUE (name);


--
-- TOC entry 3259 (class 2606 OID 49283)
-- Name: spaces spaces_unique_1; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_unique_1 UNIQUE (description);


--
-- TOC entry 3262 (class 2606 OID 49285)
-- Name: states_schedule states_schedule_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.states_schedule
    ADD CONSTRAINT states_schedule_pk PRIMARY KEY (id);


--
-- TOC entry 3264 (class 2606 OID 49287)
-- Name: states_schedule states_schedule_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.states_schedule
    ADD CONSTRAINT states_schedule_unique UNIQUE (name);


--
-- TOC entry 3268 (class 2606 OID 49289)
-- Name: member user_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- TOC entry 3270 (class 2606 OID 49291)
-- Name: member user_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT user_unique UNIQUE (dni);


--
-- TOC entry 3260 (class 1259 OID 49292)
-- Name: states_schedule_id_idx; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX states_schedule_id_idx ON public.states_schedule USING btree (id);


--
-- TOC entry 3271 (class 2606 OID 49293)
-- Name: activity activity_spaces_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_spaces_fk FOREIGN KEY (spaces_id) REFERENCES public.spaces(id);


-- Completed on 2025-03-16 12:45:38

--
-- PostgreSQL database dump complete
--


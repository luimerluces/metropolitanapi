--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: activity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.activity (
    id integer NOT NULL,
    name character varying NOT NULL,
    scheduled date NOT NULL,
    spaces_id integer NOT NULL,
);


ALTER TABLE public.activity OWNER TO admin;

--
-- Name: member; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.member (
    name character varying NOT NULL,
    dni character varying NOT NULL,
    id integer NOT NULL,
    city character varying NOT NULL,
    calendario jsonb NOT NULL
);


ALTER TABLE public.member OWNER TO admin;

--
-- Name: spaces; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.spaces (
    id integer NOT NULL,
    name character varying NOT NULL,
    description character varying NOT NULL
);


ALTER TABLE public.spaces OWNER TO admin;

--
-- Name: states_schedule; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.states_schedule (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.states_schedule OWNER TO admin;

--
-- Data for Name: activity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.activity (id, name, scheduled, spaces_id) FROM stdin;
1	padel infanil	2025-01-07  2
2	zumba	2025-01-07  4
3	spining	2025-01-07  3
4	spining	2025-02-07  3
\.


--
-- Data for Name: member; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.member (name, dni, id, city, calendario) FROM stdin;
Oscar	2456715S	2	Madrid	[{"state": 4, "actividad_id": 2}, {"state": 2, "actividad_id": 3}, {"state": 1, "actividad_id": 4}]
Luis	3264759F	1	Valencia	[{"state": 2, "actividad_id": 2}, {"state": 1, "actividad_id": 3}]
\.


--
-- Data for Name: spaces; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.spaces (id, name, description) FROM stdin;
1	padel_1	pista de padel principal
2	padel_2	pista de padel secundaria, usar en clases infantiles
3	cycling	aula para clases de cycling/spining
4	open	aula para multiples usos, clases de aerobic, yoga, zumba, etc
\.


--
-- Data for Name: states_schedule; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.states_schedule (id, name) FROM stdin;
1	reserva
2	cancelar
3	realizado
4	ausente
\.


--
-- Name: activity activity_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_pk PRIMARY KEY (id);


--
-- Name: spaces spaces_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_pk PRIMARY KEY (id);


--
-- Name: spaces spaces_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_unique UNIQUE (name);


--
-- Name: spaces spaces_unique_1; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_unique_1 UNIQUE (description);


--
-- Name: states_schedule states_schedule_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.states_schedule
    ADD CONSTRAINT states_schedule_pk PRIMARY KEY (id);


--
-- Name: states_schedule states_schedule_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.states_schedule
    ADD CONSTRAINT states_schedule_unique UNIQUE (name);


--
-- Name: member user_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT user_pk PRIMARY KEY (id);


--
-- Name: member user_unique; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.member
    ADD CONSTRAINT user_unique UNIQUE (dni);


--
-- Name: activity activity_spaces_fk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.activity
    ADD CONSTRAINT activity_spaces_fk FOREIGN KEY (spaces_id) REFERENCES public.spaces(id);


--
-- PostgreSQL database dump complete
--


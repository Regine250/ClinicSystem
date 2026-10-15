--
-- PostgreSQL database dump
--


-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

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
-- Data for Name: office; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.office VALUES (101, '34fc7f44-683a-4012-a9dc-6404c4dd38e4', 'Room 101');
INSERT INTO public.office VALUES (102, '9e8c0d0d-f261-4090-85d8-4d5f58288637', 'Room 102');
INSERT INTO public.office VALUES (103, '46178197-e1a0-48d8-895e-8ecbeaab348d', 'Room 103');
INSERT INTO public.office VALUES (104, '6edba666-886e-42f0-a50b-2e65d3023e9c', 'Room 104');


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.doctor VALUES ('1975-01-01', '14d57b89-a6d1-4dc2-accb-b512d4ba1f93', '9e8c0d0d-f261-4090-85d8-4d5f58288637', 'Alice', 'Kaneza');
INSERT INTO public.doctor VALUES ('1975-01-01', 'cb6fac76-7a23-4f06-890d-9ab6921def01', '46178197-e1a0-48d8-895e-8ecbeaab348d', 'Bob', 'Bizimana');
INSERT INTO public.doctor VALUES ('1975-01-01', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', '6edba666-886e-42f0-a50b-2e65d3023e9c', 'John', 'Mugisha');


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.patient VALUES ('1990-01-01', 'f157628b-3183-4fc9-ba7a-cedbbec66a88', 'Patient1', 'Test', 'SS1');
INSERT INTO public.patient VALUES ('1990-01-02', '2ee3b389-15e0-457d-b98c-18c1fc3ce210', 'Patient2', 'Test', 'SS2');
INSERT INTO public.patient VALUES ('1990-01-03', 'abdf1714-9dd3-49f4-8ffe-42eee969203b', 'Patient3', 'Test', 'SS3');
INSERT INTO public.patient VALUES ('1990-01-04', '71444adb-db01-49ca-b263-af0665ed661b', 'Patient4', 'Test', 'SS4');
INSERT INTO public.patient VALUES ('1990-01-05', '1df306e3-e5b6-4ef4-9117-718787ed9cf6', 'Patient5', 'Test', 'SS5');


--
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.appointment VALUES ('2026-10-20', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', 'bac9f8d2-9a4b-42d8-a9f1-d8556212c361', '2ee3b389-15e0-457d-b98c-18c1fc3ce210', 'Cancelled visit', 'CANCELLED');
INSERT INTO public.appointment VALUES ('2026-10-20', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', 'b86376df-1a1d-4ccf-bf8c-4bbb250ce2cb', '2ee3b389-15e0-457d-b98c-18c1fc3ce210', 'Cancelled visit', 'SCHEDULED');
INSERT INTO public.appointment VALUES ('2026-10-20', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', '3e88b39b-c94c-4899-b1d5-555496471c66', 'f157628b-3183-4fc9-ba7a-cedbbec66a88', 'Checkup', 'COMPLETED');
INSERT INTO public.appointment VALUES ('2026-10-22', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', '4d1a40d2-5f03-49be-a5c1-3d1894d62277', 'f157628b-3183-4fc9-ba7a-cedbbec66a88', 'Checkup', 'COMPLETED');
INSERT INTO public.appointment VALUES ('2027-01-01', '1f33095f-7b6d-4e60-8bb7-fd9cd843852e', '36930970-8721-4809-8690-7e1eece8437b', 'f157628b-3183-4fc9-ba7a-cedbbec66a88', 'Checkup', 'SCHEDULED');


--
-- Data for Name: specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.specialization VALUES ('effd5c55-d315-425a-b9de-1a8bed437f9a', 'Cardiology');
INSERT INTO public.specialization VALUES ('23846d30-a502-4337-8001-860a764015b5', 'Pediatrics');


--
-- Data for Name: doctor_specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.doctor_specialization VALUES ('14d57b89-a6d1-4dc2-accb-b512d4ba1f93', 'effd5c55-d315-425a-b9de-1a8bed437f9a');
INSERT INTO public.doctor_specialization VALUES ('14d57b89-a6d1-4dc2-accb-b512d4ba1f93', '23846d30-a502-4337-8001-860a764015b5');
INSERT INTO public.doctor_specialization VALUES ('1f33095f-7b6d-4e60-8bb7-fd9cd843852e', 'effd5c55-d315-425a-b9de-1a8bed437f9a');
INSERT INTO public.doctor_specialization VALUES ('1f33095f-7b6d-4e60-8bb7-fd9cd843852e', '23846d30-a502-4337-8001-860a764015b5');


--
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.prescription VALUES ('be9f869d-ca16-4bde-800c-a0a306cb9692', '1 tablet, 3 times a day', 7, '2026-10-15', 'Amoxicillin', '3e88b39b-c94c-4899-b1d5-555496471c66');
INSERT INTO public.prescription VALUES ('ba6e54d2-59b3-425a-9299-bc77947e603f', '1 tablet', 3, '2026-10-15', 'Paracetamol', '3e88b39b-c94c-4899-b1d5-555496471c66');


--
-- PostgreSQL database dump complete
--



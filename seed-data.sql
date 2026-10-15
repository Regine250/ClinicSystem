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

INSERT INTO public.office VALUES (101, '5937be77-9288-4fc9-9325-548294d6f749', 'Room 101');
INSERT INTO public.office VALUES (102, 'b3f26bc4-02f1-492b-9bbd-8186fa27a4d8', 'Room 102');
INSERT INTO public.office VALUES (103, 'fcc335ad-adad-4b36-9a1a-24fe5d0ffe1d', 'Room 103');
INSERT INTO public.office VALUES (104, '7b99d8c6-771b-408b-98df-e58629c5bf99', 'Room 104');


--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.doctor VALUES ('1975-01-01', '9ebda9ec-b342-4d49-a56f-171332309f68', '5937be77-9288-4fc9-9325-548294d6f749', 'John', 'Mugisha');
INSERT INTO public.doctor VALUES ('1975-01-01', 'f2a515a9-49db-4824-b40e-efb6d6047a78', 'b3f26bc4-02f1-492b-9bbd-8186fa27a4d8', 'Alice', 'Kaneza');
INSERT INTO public.doctor VALUES ('1975-01-01', '510f2f0b-d5da-4aa0-a1ec-e9a0e64becbc', 'fcc335ad-adad-4b36-9a1a-24fe5d0ffe1d', 'Bob', 'Bizimana');


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.patient VALUES ('1990-01-01', '1970f328-221c-4997-bcc4-6b797a90af23', 'Patient1', 'Test', 'SS1');
INSERT INTO public.patient VALUES ('1990-01-02', '93f6348f-267d-45a6-90f2-3fd468cd2651', 'Patient2', 'Test', 'SS2');
INSERT INTO public.patient VALUES ('1990-01-03', '54b31b2f-0192-4a4b-b365-9d024f96ad4f', 'Patient3', 'Test', 'SS3');
INSERT INTO public.patient VALUES ('1990-01-04', 'ebf2cdbb-374a-4b9a-8427-5d35083e09b3', 'Patient4', 'Test', 'SS4');
INSERT INTO public.patient VALUES ('1990-01-05', 'e5501055-573e-4162-aea4-2a81e93c2af4', 'Patient5', 'Test', 'SS5');


--
-- Data for Name: appointment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.appointment VALUES ('2026-10-20', '9ebda9ec-b342-4d49-a56f-171332309f68', 'ed067a59-e3bd-4c28-83ea-3a7cf845ab85', '1970f328-221c-4997-bcc4-6b797a90af23', 'Checkup', 'SCHEDULED');
INSERT INTO public.appointment VALUES ('2026-10-22', '9ebda9ec-b342-4d49-a56f-171332309f68', 'a3e32a20-dd42-466d-a567-0d52ecf0ba08', '1970f328-221c-4997-bcc4-6b797a90af23', 'Checkup', 'SCHEDULED');
INSERT INTO public.appointment VALUES ('2026-10-30', '9ebda9ec-b342-4d49-a56f-171332309f68', '14e40e68-9b02-47a7-be74-785b21bf2ed3', '1970f328-221c-4997-bcc4-6b797a90af23', 'Checkup', 'SCHEDULED');
INSERT INTO public.appointment VALUES ('2026-10-17', '9ebda9ec-b342-4d49-a56f-171332309f68', 'a5c514cf-5c5a-445a-aa89-5f66b98f8e7c', '1970f328-221c-4997-bcc4-6b797a90af23', 'Completed visit', 'COMPLETED');
INSERT INTO public.appointment VALUES ('2026-10-18', '9ebda9ec-b342-4d49-a56f-171332309f68', '8780e9a0-2d8e-467e-9fb9-b7db86214aba', '1970f328-221c-4997-bcc4-6b797a90af23', 'Completed visit', 'COMPLETED');
INSERT INTO public.appointment VALUES ('2026-10-20', '9ebda9ec-b342-4d49-a56f-171332309f68', '3e810696-4a19-4ab2-b58e-a2037f453c82', '93f6348f-267d-45a6-90f2-3fd468cd2651', 'Cancelled visit', 'CANCELLED');


--
-- Data for Name: specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.specialization VALUES ('cce775e5-f6db-46a4-9a52-6ec15680cdb8', 'Cardiology');
INSERT INTO public.specialization VALUES ('590e714d-b04d-4988-a899-9d2f8d1e6c5f', 'Pediatrics');


--
-- Data for Name: doctor_specialization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.doctor_specialization VALUES ('9ebda9ec-b342-4d49-a56f-171332309f68', 'cce775e5-f6db-46a4-9a52-6ec15680cdb8');
INSERT INTO public.doctor_specialization VALUES ('f2a515a9-49db-4824-b40e-efb6d6047a78', 'cce775e5-f6db-46a4-9a52-6ec15680cdb8');
INSERT INTO public.doctor_specialization VALUES ('f2a515a9-49db-4824-b40e-efb6d6047a78', '590e714d-b04d-4988-a899-9d2f8d1e6c5f');


--
-- Data for Name: prescription; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.prescription VALUES ('61dfb965-f692-49a8-9238-a3c3eaf0c92b', '1 tablet, 3 times a day', 7, '2026-10-15', 'Amoxicillin', 'a5c514cf-5c5a-445a-aa89-5f66b98f8e7c');
INSERT INTO public.prescription VALUES ('598536b7-63f5-4d2e-9c59-55bd78256fbf', '1 tablet', 3, '2026-10-15', 'Paracetamol', 'a5c514cf-5c5a-445a-aa89-5f66b98f8e7c');


--
-- PostgreSQL database dump complete
--



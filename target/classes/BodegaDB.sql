PGDMP                         y           BodegaDB    13.1    13.1 !    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16394    BodegaDB    DATABASE     i   CREATE DATABASE "BodegaDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Paraguay.1252';
    DROP DATABASE "BodegaDB";
                postgres    false            �            1259    16807    persona    TABLE     $  CREATE TABLE public.persona (
    persona integer NOT NULL,
    nombre character varying NOT NULL,
    apellido character varying NOT NULL,
    direccion character varying NOT NULL,
    numero_documento character varying NOT NULL,
    ruc character varying,
    telefono character varying
);
    DROP TABLE public.persona;
       public         heap    postgres    false            �            1259    16805    persona_persona_seq    SEQUENCE     �   CREATE SEQUENCE public.persona_persona_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.persona_persona_seq;
       public          postgres    false    203            �           0    0    persona_persona_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.persona_persona_seq OWNED BY public.persona.persona;
          public          postgres    false    202            �            1259    16818    producto    TABLE     l   CREATE TABLE public.producto (
    producto integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.producto;
       public         heap    postgres    false            �            1259    16816    producto_producto_seq    SEQUENCE     �   CREATE SEQUENCE public.producto_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.producto_producto_seq;
       public          postgres    false    205            �           0    0    producto_producto_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.producto_producto_seq OWNED BY public.producto.producto;
          public          postgres    false    204            �            1259    16829 	   proveedor    TABLE     n   CREATE TABLE public.proveedor (
    proveedor integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.proveedor;
       public         heap    postgres    false            �            1259    16827    proveedor_proveedor_seq    SEQUENCE     �   CREATE SEQUENCE public.proveedor_proveedor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.proveedor_proveedor_seq;
       public          postgres    false    207            �           0    0    proveedor_proveedor_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.proveedor_proveedor_seq OWNED BY public.proveedor.proveedor;
          public          postgres    false    206            �            1259    16395    usuario    TABLE     �   CREATE TABLE public.usuario (
    usuario integer NOT NULL,
    clave character varying NOT NULL,
    persona integer NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    16398    usuario_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.usuario_usuario_seq;
       public          postgres    false    200            �           0    0    usuario_usuario_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.usuario_usuario_seq OWNED BY public.usuario.usuario;
          public          postgres    false    201            9           2604    16810    persona persona    DEFAULT     r   ALTER TABLE ONLY public.persona ALTER COLUMN persona SET DEFAULT nextval('public.persona_persona_seq'::regclass);
 >   ALTER TABLE public.persona ALTER COLUMN persona DROP DEFAULT;
       public          postgres    false    203    202    203            :           2604    16821    producto producto    DEFAULT     v   ALTER TABLE ONLY public.producto ALTER COLUMN producto SET DEFAULT nextval('public.producto_producto_seq'::regclass);
 @   ALTER TABLE public.producto ALTER COLUMN producto DROP DEFAULT;
       public          postgres    false    205    204    205            ;           2604    16832    proveedor proveedor    DEFAULT     z   ALTER TABLE ONLY public.proveedor ALTER COLUMN proveedor SET DEFAULT nextval('public.proveedor_proveedor_seq'::regclass);
 B   ALTER TABLE public.proveedor ALTER COLUMN proveedor DROP DEFAULT;
       public          postgres    false    206    207    207            8           2604    16400    usuario usuario    DEFAULT     r   ALTER TABLE ONLY public.usuario ALTER COLUMN usuario SET DEFAULT nextval('public.usuario_usuario_seq'::regclass);
 >   ALTER TABLE public.usuario ALTER COLUMN usuario DROP DEFAULT;
       public          postgres    false    201    200            �          0    16807    persona 
   TABLE DATA           h   COPY public.persona (persona, nombre, apellido, direccion, numero_documento, ruc, telefono) FROM stdin;
    public          postgres    false    203   �#       �          0    16818    producto 
   TABLE DATA           9   COPY public.producto (producto, descripcion) FROM stdin;
    public          postgres    false    205   �#       �          0    16829 	   proveedor 
   TABLE DATA           ;   COPY public.proveedor (proveedor, descripcion) FROM stdin;
    public          postgres    false    207   �#       �          0    16395    usuario 
   TABLE DATA           :   COPY public.usuario (usuario, clave, persona) FROM stdin;
    public          postgres    false    200   �#       �           0    0    persona_persona_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.persona_persona_seq', 1, false);
          public          postgres    false    202            �           0    0    producto_producto_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.producto_producto_seq', 1, false);
          public          postgres    false    204            �           0    0    proveedor_proveedor_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.proveedor_proveedor_seq', 1, false);
          public          postgres    false    206            �           0    0    usuario_usuario_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.usuario_usuario_seq', 1, false);
          public          postgres    false    201            ?           2606    16815    persona persona_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (persona);
 >   ALTER TABLE ONLY public.persona DROP CONSTRAINT persona_pkey;
       public            postgres    false    203            A           2606    16826    producto producto_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (producto);
 @   ALTER TABLE ONLY public.producto DROP CONSTRAINT producto_pkey;
       public            postgres    false    205            C           2606    16837    proveedor proveedor_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (proveedor);
 B   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_pkey;
       public            postgres    false    207            =           2606    16408    usuario usuario_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    200            D           2606    16838    usuario usuario_persona_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_persona_fkey FOREIGN KEY (persona) REFERENCES public.persona(persona) NOT VALID;
 F   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_persona_fkey;
       public          postgres    false    203    200    2879            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     
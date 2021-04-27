PGDMP     !    5                y            BodegaDB    13.1    13.1 9    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16587    BodegaDB    DATABASE     i   CREATE DATABASE "BodegaDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Paraguay.1252';
    DROP DATABASE "BodegaDB";
                postgres    false            �            1259    16699    marca    TABLE     f   CREATE TABLE public.marca (
    marca integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.marca;
       public         heap    postgres    false            �            1259    16697    marca_marca_seq    SEQUENCE     �   CREATE SEQUENCE public.marca_marca_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.marca_marca_seq;
       public          postgres    false    213            �           0    0    marca_marca_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.marca_marca_seq OWNED BY public.marca.marca;
          public          postgres    false    212            �            1259    16588    persona    TABLE     $  CREATE TABLE public.persona (
    persona integer NOT NULL,
    nombre character varying NOT NULL,
    apellido character varying NOT NULL,
    direccion character varying NOT NULL,
    numero_documento character varying NOT NULL,
    ruc character varying,
    telefono character varying
);
    DROP TABLE public.persona;
       public         heap    postgres    false            �            1259    16594    persona_persona_seq    SEQUENCE     �   CREATE SEQUENCE public.persona_persona_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.persona_persona_seq;
       public          postgres    false    200            �           0    0    persona_persona_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.persona_persona_seq OWNED BY public.persona.persona;
          public          postgres    false    201            �            1259    16648    producto    TABLE     �   CREATE TABLE public.producto (
    producto integer NOT NULL,
    tipo_producto integer NOT NULL,
    precio double precision NOT NULL,
    fecha_vencimiento date NOT NULL
);
    DROP TABLE public.producto;
       public         heap    postgres    false            �            1259    16646    producto_producto_seq    SEQUENCE     �   CREATE SEQUENCE public.producto_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.producto_producto_seq;
       public          postgres    false    207            �           0    0    producto_producto_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.producto_producto_seq OWNED BY public.producto.producto;
          public          postgres    false    206            �            1259    16604 	   proveedor    TABLE     n   CREATE TABLE public.proveedor (
    proveedor integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.proveedor;
       public         heap    postgres    false            �            1259    16610    proveedor_proveedor_seq    SEQUENCE     �   CREATE SEQUENCE public.proveedor_proveedor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.proveedor_proveedor_seq;
       public          postgres    false    202            �           0    0    proveedor_proveedor_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.proveedor_proveedor_seq OWNED BY public.proveedor.proveedor;
          public          postgres    false    203            �            1259    16678    tamaño    TABLE     �   CREATE TABLE public."tamaño" (
    "tamaño" integer NOT NULL,
    descripcion character varying NOT NULL,
    ml double precision NOT NULL
);
    DROP TABLE public."tamaño";
       public         heap    postgres    false            �            1259    16676    tamaño_tamaño_seq    SEQUENCE     �   CREATE SEQUENCE public."tamaño_tamaño_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."tamaño_tamaño_seq";
       public          postgres    false    211            �           0    0    tamaño_tamaño_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public."tamaño_tamaño_seq" OWNED BY public."tamaño"."tamaño";
          public          postgres    false    210            �            1259    16656    tipo_producto    TABLE     �   CREATE TABLE public.tipo_producto (
    tipo_producto integer NOT NULL,
    marca integer NOT NULL,
    "tamaño" integer NOT NULL,
    descripcion character varying NOT NULL
);
 !   DROP TABLE public.tipo_producto;
       public         heap    postgres    false            �            1259    16654    tipo_producto_tipo_producto_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_producto_tipo_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.tipo_producto_tipo_producto_seq;
       public          postgres    false    209                        0    0    tipo_producto_tipo_producto_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.tipo_producto_tipo_producto_seq OWNED BY public.tipo_producto.tipo_producto;
          public          postgres    false    208            �            1259    16612    usuario    TABLE     �   CREATE TABLE public.usuario (
    usuario integer NOT NULL,
    clave character varying NOT NULL,
    persona integer NOT NULL,
    alias character varying NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    16618    usuario_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.usuario_usuario_seq;
       public          postgres    false    204                       0    0    usuario_usuario_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.usuario_usuario_seq OWNED BY public.usuario.usuario;
          public          postgres    false    205            R           2604    16702    marca marca    DEFAULT     j   ALTER TABLE ONLY public.marca ALTER COLUMN marca SET DEFAULT nextval('public.marca_marca_seq'::regclass);
 :   ALTER TABLE public.marca ALTER COLUMN marca DROP DEFAULT;
       public          postgres    false    212    213    213            L           2604    16620    persona persona    DEFAULT     r   ALTER TABLE ONLY public.persona ALTER COLUMN persona SET DEFAULT nextval('public.persona_persona_seq'::regclass);
 >   ALTER TABLE public.persona ALTER COLUMN persona DROP DEFAULT;
       public          postgres    false    201    200            O           2604    16651    producto producto    DEFAULT     v   ALTER TABLE ONLY public.producto ALTER COLUMN producto SET DEFAULT nextval('public.producto_producto_seq'::regclass);
 @   ALTER TABLE public.producto ALTER COLUMN producto DROP DEFAULT;
       public          postgres    false    206    207    207            M           2604    16622    proveedor proveedor    DEFAULT     z   ALTER TABLE ONLY public.proveedor ALTER COLUMN proveedor SET DEFAULT nextval('public.proveedor_proveedor_seq'::regclass);
 B   ALTER TABLE public.proveedor ALTER COLUMN proveedor DROP DEFAULT;
       public          postgres    false    203    202            Q           2604    16681    tamaño tamaño    DEFAULT     x   ALTER TABLE ONLY public."tamaño" ALTER COLUMN "tamaño" SET DEFAULT nextval('public."tamaño_tamaño_seq"'::regclass);
 B   ALTER TABLE public."tamaño" ALTER COLUMN "tamaño" DROP DEFAULT;
       public          postgres    false    210    211    211            P           2604    16659    tipo_producto tipo_producto    DEFAULT     �   ALTER TABLE ONLY public.tipo_producto ALTER COLUMN tipo_producto SET DEFAULT nextval('public.tipo_producto_tipo_producto_seq'::regclass);
 J   ALTER TABLE public.tipo_producto ALTER COLUMN tipo_producto DROP DEFAULT;
       public          postgres    false    208    209    209            N           2604    16623    usuario usuario    DEFAULT     r   ALTER TABLE ONLY public.usuario ALTER COLUMN usuario SET DEFAULT nextval('public.usuario_usuario_seq'::regclass);
 >   ALTER TABLE public.usuario ALTER COLUMN usuario DROP DEFAULT;
       public          postgres    false    205    204            �          0    16699    marca 
   TABLE DATA           3   COPY public.marca (marca, descripcion) FROM stdin;
    public          postgres    false    213   L@       �          0    16588    persona 
   TABLE DATA           h   COPY public.persona (persona, nombre, apellido, direccion, numero_documento, ruc, telefono) FROM stdin;
    public          postgres    false    200   i@       �          0    16648    producto 
   TABLE DATA           V   COPY public.producto (producto, tipo_producto, precio, fecha_vencimiento) FROM stdin;
    public          postgres    false    207   �@       �          0    16604 	   proveedor 
   TABLE DATA           ;   COPY public.proveedor (proveedor, descripcion) FROM stdin;
    public          postgres    false    202   �@       �          0    16678    tamaño 
   TABLE DATA           ?   COPY public."tamaño" ("tamaño", descripcion, ml) FROM stdin;
    public          postgres    false    211   �@       �          0    16656    tipo_producto 
   TABLE DATA           U   COPY public.tipo_producto (tipo_producto, marca, "tamaño", descripcion) FROM stdin;
    public          postgres    false    209   �@       �          0    16612    usuario 
   TABLE DATA           A   COPY public.usuario (usuario, clave, persona, alias) FROM stdin;
    public          postgres    false    204   A                  0    0    marca_marca_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.marca_marca_seq', 1, false);
          public          postgres    false    212                       0    0    persona_persona_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.persona_persona_seq', 1, true);
          public          postgres    false    201                       0    0    producto_producto_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.producto_producto_seq', 1, false);
          public          postgres    false    206                       0    0    proveedor_proveedor_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.proveedor_proveedor_seq', 1, false);
          public          postgres    false    203                       0    0    tamaño_tamaño_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."tamaño_tamaño_seq"', 1, false);
          public          postgres    false    210                       0    0    tipo_producto_tipo_producto_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.tipo_producto_tipo_producto_seq', 1, false);
          public          postgres    false    208                       0    0    usuario_usuario_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.usuario_usuario_seq', 1, true);
          public          postgres    false    205            `           2606    16707    marca marca_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.marca
    ADD CONSTRAINT marca_pkey PRIMARY KEY (marca);
 :   ALTER TABLE ONLY public.marca DROP CONSTRAINT marca_pkey;
       public            postgres    false    213            T           2606    16625    persona persona_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (persona);
 >   ALTER TABLE ONLY public.persona DROP CONSTRAINT persona_pkey;
       public            postgres    false    200            Z           2606    16653    producto producto_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (producto);
 @   ALTER TABLE ONLY public.producto DROP CONSTRAINT producto_pkey;
       public            postgres    false    207            V           2606    16629    proveedor proveedor_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (proveedor);
 B   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_pkey;
       public            postgres    false    202            ^           2606    16686    tamaño tamaño_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public."tamaño"
    ADD CONSTRAINT "tamaño_pkey" PRIMARY KEY ("tamaño");
 B   ALTER TABLE ONLY public."tamaño" DROP CONSTRAINT "tamaño_pkey";
       public            postgres    false    211            \           2606    16664     tipo_producto tipo_producto_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT tipo_producto_pkey PRIMARY KEY (tipo_producto);
 J   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT tipo_producto_pkey;
       public            postgres    false    209            X           2606    16631    usuario usuario_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    204            b           2606    16687 $   producto producto_tipo_producto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_tipo_producto_fkey FOREIGN KEY (tipo_producto) REFERENCES public.tipo_producto(tipo_producto) NOT VALID;
 N   ALTER TABLE ONLY public.producto DROP CONSTRAINT producto_tipo_producto_fkey;
       public          postgres    false    2908    209    207            d           2606    16708 &   tipo_producto tipo_producto_marca_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT tipo_producto_marca_fkey FOREIGN KEY (marca) REFERENCES public.marca(marca) NOT VALID;
 P   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT tipo_producto_marca_fkey;
       public          postgres    false    209    213    2912            c           2606    16692 (   tipo_producto tipo_producto_tamaño_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT "tipo_producto_tamaño_fkey" FOREIGN KEY ("tamaño") REFERENCES public."tamaño"("tamaño") NOT VALID;
 T   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT "tipo_producto_tamaño_fkey";
       public          postgres    false    2910    211    209            a           2606    16632    usuario usuario_persona_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_persona_fkey FOREIGN KEY (persona) REFERENCES public.persona(persona) NOT VALID;
 F   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_persona_fkey;
       public          postgres    false    204    2900    200            �      x������ � �      �   +   x�3�t+JMI�tJ,J�,�笨��442� �? ����� ��
A      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �   #   x�3�LJ-�H��L��/�4�L+JMI����� y)�     
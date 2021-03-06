PGDMP                         y            BodegaDB    13.3    13.3 z    ]           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ^           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            _           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            `           1262    30950    BodegaDB    DATABASE     i   CREATE DATABASE "BodegaDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Paraguay.1252';
    DROP DATABASE "BodegaDB";
                postgres    false            �            1259    31121    cargo    TABLE     f   CREATE TABLE public.cargo (
    cargo integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.cargo;
       public         heap    postgres    false            �            1259    31119    cargo_cargo_seq    SEQUENCE     �   CREATE SEQUENCE public.cargo_cargo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.cargo_cargo_seq;
       public          postgres    false    225            a           0    0    cargo_cargo_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.cargo_cargo_seq OWNED BY public.cargo.cargo;
          public          postgres    false    224            �            1259    31047    egreso    TABLE       CREATE TABLE public.egreso (
    egreso integer NOT NULL,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    usuario integer NOT NULL,
    proveedor integer,
    empleado integer,
    tipo_egreso integer NOT NULL,
    contado boolean NOT NULL
);
    DROP TABLE public.egreso;
       public         heap    postgres    false            �            1259    31045    egreso_egreso_seq    SEQUENCE     �   CREATE SEQUENCE public.egreso_egreso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.egreso_egreso_seq;
       public          postgres    false    215            b           0    0    egreso_egreso_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.egreso_egreso_seq OWNED BY public.egreso.egreso;
          public          postgres    false    214            �            1259    31112    empleado    TABLE     �   CREATE TABLE public.empleado (
    empleado integer NOT NULL,
    sueldo double precision NOT NULL,
    persona integer NOT NULL,
    cargo integer NOT NULL,
    turno integer NOT NULL
);
    DROP TABLE public.empleado;
       public         heap    postgres    false            �            1259    31110    empleado_empleado_seq    SEQUENCE     �   CREATE SEQUENCE public.empleado_empleado_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.empleado_empleado_seq;
       public          postgres    false    223            c           0    0    empleado_empleado_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.empleado_empleado_seq OWNED BY public.empleado.empleado;
          public          postgres    false    222            �            1259    39239    genero    TABLE     h   CREATE TABLE public.genero (
    genero integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.genero;
       public         heap    postgres    false            �            1259    39237    genero_genero_seq    SEQUENCE     �   CREATE SEQUENCE public.genero_genero_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.genero_genero_seq;
       public          postgres    false    229            d           0    0    genero_genero_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.genero_genero_seq OWNED BY public.genero.genero;
          public          postgres    false    228            �            1259    31089    ingreso    TABLE       CREATE TABLE public.ingreso (
    ingreso integer NOT NULL,
    fecha date NOT NULL,
    monto double precision NOT NULL,
    producto integer NOT NULL,
    total double precision NOT NULL,
    cantidad double precision NOT NULL,
    usuario integer NOT NULL
);
    DROP TABLE public.ingreso;
       public         heap    postgres    false            �            1259    31087    ingreso_ingreso_seq    SEQUENCE     �   CREATE SEQUENCE public.ingreso_ingreso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.ingreso_ingreso_seq;
       public          postgres    false    221            e           0    0    ingreso_ingreso_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.ingreso_ingreso_seq OWNED BY public.ingreso.ingreso;
          public          postgres    false    220            �            1259    30951    marca    TABLE     f   CREATE TABLE public.marca (
    marca integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.marca;
       public         heap    postgres    false            �            1259    30957    marca_marca_seq    SEQUENCE     �   CREATE SEQUENCE public.marca_marca_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.marca_marca_seq;
       public          postgres    false    200            f           0    0    marca_marca_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.marca_marca_seq OWNED BY public.marca.marca;
          public          postgres    false    201            �            1259    30959    persona    TABLE     S  CREATE TABLE public.persona (
    persona integer NOT NULL,
    nombre character varying NOT NULL,
    apellido character varying NOT NULL,
    direccion character varying NOT NULL,
    numero_documento character varying NOT NULL,
    ruc character varying,
    telefono character varying,
    genero integer,
    fecha_nacimiento date
);
    DROP TABLE public.persona;
       public         heap    postgres    false            �            1259    30965    persona_persona_seq    SEQUENCE     �   CREATE SEQUENCE public.persona_persona_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.persona_persona_seq;
       public          postgres    false    202            g           0    0    persona_persona_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.persona_persona_seq OWNED BY public.persona.persona;
          public          postgres    false    203            �            1259    30967    producto    TABLE     �   CREATE TABLE public.producto (
    producto integer NOT NULL,
    tipo_producto integer NOT NULL,
    precio double precision NOT NULL,
    fecha_vencimiento date NOT NULL
);
    DROP TABLE public.producto;
       public         heap    postgres    false            �            1259    30970    producto_producto_seq    SEQUENCE     �   CREATE SEQUENCE public.producto_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.producto_producto_seq;
       public          postgres    false    204            h           0    0    producto_producto_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.producto_producto_seq OWNED BY public.producto.producto;
          public          postgres    false    205            �            1259    30972 	   proveedor    TABLE     n   CREATE TABLE public.proveedor (
    proveedor integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.proveedor;
       public         heap    postgres    false            �            1259    30978    proveedor_proveedor_seq    SEQUENCE     �   CREATE SEQUENCE public.proveedor_proveedor_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.proveedor_proveedor_seq;
       public          postgres    false    206            i           0    0    proveedor_proveedor_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.proveedor_proveedor_seq OWNED BY public.proveedor.proveedor;
          public          postgres    false    207            �            1259    30980    tamanho    TABLE     �   CREATE TABLE public.tamanho (
    tamanho integer NOT NULL,
    descripcion character varying NOT NULL,
    ml double precision NOT NULL
);
    DROP TABLE public.tamanho;
       public         heap    postgres    false            �            1259    30986    tamaño_tamaño_seq    SEQUENCE     �   CREATE SEQUENCE public."tamaño_tamaño_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."tamaño_tamaño_seq";
       public          postgres    false    208            j           0    0    tamaño_tamaño_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."tamaño_tamaño_seq" OWNED BY public.tamanho.tamanho;
          public          postgres    false    209            �            1259    31055    tipo_egreso    TABLE     r   CREATE TABLE public.tipo_egreso (
    tipo_egreso integer NOT NULL,
    descripcion character varying NOT NULL
);
    DROP TABLE public.tipo_egreso;
       public         heap    postgres    false            �            1259    31053    tipo_egreso_tipo_egreso_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_egreso_tipo_egreso_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.tipo_egreso_tipo_egreso_seq;
       public          postgres    false    217            k           0    0    tipo_egreso_tipo_egreso_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.tipo_egreso_tipo_egreso_seq OWNED BY public.tipo_egreso.tipo_egreso;
          public          postgres    false    216            �            1259    30988    tipo_producto    TABLE     �   CREATE TABLE public.tipo_producto (
    tipo_producto integer NOT NULL,
    marca integer NOT NULL,
    tamanho integer NOT NULL,
    descripcion character varying NOT NULL
);
 !   DROP TABLE public.tipo_producto;
       public         heap    postgres    false            �            1259    30994    tipo_producto_tipo_producto_seq    SEQUENCE     �   CREATE SEQUENCE public.tipo_producto_tipo_producto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.tipo_producto_tipo_producto_seq;
       public          postgres    false    210            l           0    0    tipo_producto_tipo_producto_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.tipo_producto_tipo_producto_seq OWNED BY public.tipo_producto.tipo_producto;
          public          postgres    false    211            �            1259    31071    transaccion    TABLE       CREATE TABLE public.transaccion (
    transaccion integer NOT NULL,
    producto integer,
    monto double precision NOT NULL,
    fecha date NOT NULL,
    observacion character varying,
    egreso integer NOT NULL,
    cantidad double precision NOT NULL
);
    DROP TABLE public.transaccion;
       public         heap    postgres    false            �            1259    31069    transaccion_transaccion_seq    SEQUENCE     �   CREATE SEQUENCE public.transaccion_transaccion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.transaccion_transaccion_seq;
       public          postgres    false    219            m           0    0    transaccion_transaccion_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE public.transaccion_transaccion_seq OWNED BY public.transaccion.transaccion;
          public          postgres    false    218            �            1259    31132    turno    TABLE     �   CREATE TABLE public.turno (
    turno integer NOT NULL,
    descripcion character varying NOT NULL,
    horario_entrada character varying NOT NULL,
    horario_salida character varying NOT NULL
);
    DROP TABLE public.turno;
       public         heap    postgres    false            �            1259    31130    turno_turno_seq    SEQUENCE     �   CREATE SEQUENCE public.turno_turno_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.turno_turno_seq;
       public          postgres    false    227            n           0    0    turno_turno_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.turno_turno_seq OWNED BY public.turno.turno;
          public          postgres    false    226            �            1259    30996    usuario    TABLE     �   CREATE TABLE public.usuario (
    usuario integer NOT NULL,
    clave character varying NOT NULL,
    persona integer NOT NULL,
    alias character varying NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    31002    usuario_usuario_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.usuario_usuario_seq;
       public          postgres    false    212            o           0    0    usuario_usuario_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.usuario_usuario_seq OWNED BY public.usuario.usuario;
          public          postgres    false    213            �           2604    31124    cargo cargo    DEFAULT     j   ALTER TABLE ONLY public.cargo ALTER COLUMN cargo SET DEFAULT nextval('public.cargo_cargo_seq'::regclass);
 :   ALTER TABLE public.cargo ALTER COLUMN cargo DROP DEFAULT;
       public          postgres    false    224    225    225            �           2604    31050    egreso egreso    DEFAULT     n   ALTER TABLE ONLY public.egreso ALTER COLUMN egreso SET DEFAULT nextval('public.egreso_egreso_seq'::regclass);
 <   ALTER TABLE public.egreso ALTER COLUMN egreso DROP DEFAULT;
       public          postgres    false    214    215    215            �           2604    31115    empleado empleado    DEFAULT     v   ALTER TABLE ONLY public.empleado ALTER COLUMN empleado SET DEFAULT nextval('public.empleado_empleado_seq'::regclass);
 @   ALTER TABLE public.empleado ALTER COLUMN empleado DROP DEFAULT;
       public          postgres    false    223    222    223            �           2604    39242    genero genero    DEFAULT     n   ALTER TABLE ONLY public.genero ALTER COLUMN genero SET DEFAULT nextval('public.genero_genero_seq'::regclass);
 <   ALTER TABLE public.genero ALTER COLUMN genero DROP DEFAULT;
       public          postgres    false    229    228    229            �           2604    31092    ingreso ingreso    DEFAULT     r   ALTER TABLE ONLY public.ingreso ALTER COLUMN ingreso SET DEFAULT nextval('public.ingreso_ingreso_seq'::regclass);
 >   ALTER TABLE public.ingreso ALTER COLUMN ingreso DROP DEFAULT;
       public          postgres    false    220    221    221            �           2604    31004    marca marca    DEFAULT     j   ALTER TABLE ONLY public.marca ALTER COLUMN marca SET DEFAULT nextval('public.marca_marca_seq'::regclass);
 :   ALTER TABLE public.marca ALTER COLUMN marca DROP DEFAULT;
       public          postgres    false    201    200            �           2604    31005    persona persona    DEFAULT     r   ALTER TABLE ONLY public.persona ALTER COLUMN persona SET DEFAULT nextval('public.persona_persona_seq'::regclass);
 >   ALTER TABLE public.persona ALTER COLUMN persona DROP DEFAULT;
       public          postgres    false    203    202            �           2604    31006    producto producto    DEFAULT     v   ALTER TABLE ONLY public.producto ALTER COLUMN producto SET DEFAULT nextval('public.producto_producto_seq'::regclass);
 @   ALTER TABLE public.producto ALTER COLUMN producto DROP DEFAULT;
       public          postgres    false    205    204            �           2604    31007    proveedor proveedor    DEFAULT     z   ALTER TABLE ONLY public.proveedor ALTER COLUMN proveedor SET DEFAULT nextval('public.proveedor_proveedor_seq'::regclass);
 B   ALTER TABLE public.proveedor ALTER COLUMN proveedor DROP DEFAULT;
       public          postgres    false    207    206            �           2604    31008    tamanho tamanho    DEFAULT     t   ALTER TABLE ONLY public.tamanho ALTER COLUMN tamanho SET DEFAULT nextval('public."tamaño_tamaño_seq"'::regclass);
 >   ALTER TABLE public.tamanho ALTER COLUMN tamanho DROP DEFAULT;
       public          postgres    false    209    208            �           2604    31058    tipo_egreso tipo_egreso    DEFAULT     �   ALTER TABLE ONLY public.tipo_egreso ALTER COLUMN tipo_egreso SET DEFAULT nextval('public.tipo_egreso_tipo_egreso_seq'::regclass);
 F   ALTER TABLE public.tipo_egreso ALTER COLUMN tipo_egreso DROP DEFAULT;
       public          postgres    false    217    216    217            �           2604    31009    tipo_producto tipo_producto    DEFAULT     �   ALTER TABLE ONLY public.tipo_producto ALTER COLUMN tipo_producto SET DEFAULT nextval('public.tipo_producto_tipo_producto_seq'::regclass);
 J   ALTER TABLE public.tipo_producto ALTER COLUMN tipo_producto DROP DEFAULT;
       public          postgres    false    211    210            �           2604    31074    transaccion transaccion    DEFAULT     �   ALTER TABLE ONLY public.transaccion ALTER COLUMN transaccion SET DEFAULT nextval('public.transaccion_transaccion_seq'::regclass);
 F   ALTER TABLE public.transaccion ALTER COLUMN transaccion DROP DEFAULT;
       public          postgres    false    218    219    219            �           2604    31135    turno turno    DEFAULT     j   ALTER TABLE ONLY public.turno ALTER COLUMN turno SET DEFAULT nextval('public.turno_turno_seq'::regclass);
 :   ALTER TABLE public.turno ALTER COLUMN turno DROP DEFAULT;
       public          postgres    false    226    227    227            �           2604    31010    usuario usuario    DEFAULT     r   ALTER TABLE ONLY public.usuario ALTER COLUMN usuario SET DEFAULT nextval('public.usuario_usuario_seq'::regclass);
 >   ALTER TABLE public.usuario ALTER COLUMN usuario DROP DEFAULT;
       public          postgres    false    213    212            V          0    31121    cargo 
   TABLE DATA           3   COPY public.cargo (cargo, descripcion) FROM stdin;
    public          postgres    false    225   C�       L          0    31047    egreso 
   TABLE DATA           j   COPY public.egreso (egreso, monto, fecha, usuario, proveedor, empleado, tipo_egreso, contado) FROM stdin;
    public          postgres    false    215   `�       T          0    31112    empleado 
   TABLE DATA           K   COPY public.empleado (empleado, sueldo, persona, cargo, turno) FROM stdin;
    public          postgres    false    223   }�       Z          0    39239    genero 
   TABLE DATA           5   COPY public.genero (genero, descripcion) FROM stdin;
    public          postgres    false    229   ��       R          0    31089    ingreso 
   TABLE DATA           \   COPY public.ingreso (ingreso, fecha, monto, producto, total, cantidad, usuario) FROM stdin;
    public          postgres    false    221   ˌ       =          0    30951    marca 
   TABLE DATA           3   COPY public.marca (marca, descripcion) FROM stdin;
    public          postgres    false    200   �       ?          0    30959    persona 
   TABLE DATA           �   COPY public.persona (persona, nombre, apellido, direccion, numero_documento, ruc, telefono, genero, fecha_nacimiento) FROM stdin;
    public          postgres    false    202   �       A          0    30967    producto 
   TABLE DATA           V   COPY public.producto (producto, tipo_producto, precio, fecha_vencimiento) FROM stdin;
    public          postgres    false    204   e�       C          0    30972 	   proveedor 
   TABLE DATA           ;   COPY public.proveedor (proveedor, descripcion) FROM stdin;
    public          postgres    false    206   ��       E          0    30980    tamanho 
   TABLE DATA           ;   COPY public.tamanho (tamanho, descripcion, ml) FROM stdin;
    public          postgres    false    208   ��       N          0    31055    tipo_egreso 
   TABLE DATA           ?   COPY public.tipo_egreso (tipo_egreso, descripcion) FROM stdin;
    public          postgres    false    217   ��       G          0    30988    tipo_producto 
   TABLE DATA           S   COPY public.tipo_producto (tipo_producto, marca, tamanho, descripcion) FROM stdin;
    public          postgres    false    210   ٍ       P          0    31071    transaccion 
   TABLE DATA           i   COPY public.transaccion (transaccion, producto, monto, fecha, observacion, egreso, cantidad) FROM stdin;
    public          postgres    false    219   ��       X          0    31132    turno 
   TABLE DATA           T   COPY public.turno (turno, descripcion, horario_entrada, horario_salida) FROM stdin;
    public          postgres    false    227   �       I          0    30996    usuario 
   TABLE DATA           A   COPY public.usuario (usuario, clave, persona, alias) FROM stdin;
    public          postgres    false    212   0�       p           0    0    cargo_cargo_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.cargo_cargo_seq', 1, false);
          public          postgres    false    224            q           0    0    egreso_egreso_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.egreso_egreso_seq', 1, false);
          public          postgres    false    214            r           0    0    empleado_empleado_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.empleado_empleado_seq', 1, false);
          public          postgres    false    222            s           0    0    genero_genero_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.genero_genero_seq', 1, false);
          public          postgres    false    228            t           0    0    ingreso_ingreso_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.ingreso_ingreso_seq', 1, false);
          public          postgres    false    220            u           0    0    marca_marca_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.marca_marca_seq', 1, false);
          public          postgres    false    201            v           0    0    persona_persona_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.persona_persona_seq', 2, true);
          public          postgres    false    203            w           0    0    producto_producto_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.producto_producto_seq', 1, false);
          public          postgres    false    205            x           0    0    proveedor_proveedor_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.proveedor_proveedor_seq', 1, false);
          public          postgres    false    207            y           0    0    tamaño_tamaño_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."tamaño_tamaño_seq"', 1, false);
          public          postgres    false    209            z           0    0    tipo_egreso_tipo_egreso_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.tipo_egreso_tipo_egreso_seq', 1, false);
          public          postgres    false    216            {           0    0    tipo_producto_tipo_producto_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.tipo_producto_tipo_producto_seq', 1, false);
          public          postgres    false    211            |           0    0    transaccion_transaccion_seq    SEQUENCE SET     J   SELECT pg_catalog.setval('public.transaccion_transaccion_seq', 1, false);
          public          postgres    false    218            }           0    0    turno_turno_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.turno_turno_seq', 1, false);
          public          postgres    false    226            ~           0    0    usuario_usuario_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.usuario_usuario_seq', 2, true);
          public          postgres    false    213            �           2606    31129    cargo cargo_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.cargo
    ADD CONSTRAINT cargo_pkey PRIMARY KEY (cargo);
 :   ALTER TABLE ONLY public.cargo DROP CONSTRAINT cargo_pkey;
       public            postgres    false    225            �           2606    31052    egreso egreso_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.egreso
    ADD CONSTRAINT egreso_pkey PRIMARY KEY (egreso);
 <   ALTER TABLE ONLY public.egreso DROP CONSTRAINT egreso_pkey;
       public            postgres    false    215            �           2606    31117    empleado empleado_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (empleado);
 @   ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_pkey;
       public            postgres    false    223            �           2606    39247    genero genero_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.genero
    ADD CONSTRAINT genero_pkey PRIMARY KEY (genero);
 <   ALTER TABLE ONLY public.genero DROP CONSTRAINT genero_pkey;
       public            postgres    false    229            �           2606    31094    ingreso ingreso_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.ingreso
    ADD CONSTRAINT ingreso_pkey PRIMARY KEY (ingreso);
 >   ALTER TABLE ONLY public.ingreso DROP CONSTRAINT ingreso_pkey;
       public            postgres    false    221            �           2606    31012    marca marca_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.marca
    ADD CONSTRAINT marca_pkey PRIMARY KEY (marca);
 :   ALTER TABLE ONLY public.marca DROP CONSTRAINT marca_pkey;
       public            postgres    false    200            �           2606    31014    persona persona_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (persona);
 >   ALTER TABLE ONLY public.persona DROP CONSTRAINT persona_pkey;
       public            postgres    false    202            �           2606    31016    producto producto_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_pkey PRIMARY KEY (producto);
 @   ALTER TABLE ONLY public.producto DROP CONSTRAINT producto_pkey;
       public            postgres    false    204            �           2606    31018    proveedor proveedor_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.proveedor
    ADD CONSTRAINT proveedor_pkey PRIMARY KEY (proveedor);
 B   ALTER TABLE ONLY public.proveedor DROP CONSTRAINT proveedor_pkey;
       public            postgres    false    206            �           2606    31020    tamanho tamaño_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public.tamanho
    ADD CONSTRAINT "tamaño_pkey" PRIMARY KEY (tamanho);
 @   ALTER TABLE ONLY public.tamanho DROP CONSTRAINT "tamaño_pkey";
       public            postgres    false    208            �           2606    31063    tipo_egreso tipo_egreso_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.tipo_egreso
    ADD CONSTRAINT tipo_egreso_pkey PRIMARY KEY (tipo_egreso);
 F   ALTER TABLE ONLY public.tipo_egreso DROP CONSTRAINT tipo_egreso_pkey;
       public            postgres    false    217            �           2606    31022     tipo_producto tipo_producto_pkey 
   CONSTRAINT     i   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT tipo_producto_pkey PRIMARY KEY (tipo_producto);
 J   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT tipo_producto_pkey;
       public            postgres    false    210            �           2606    31079    transaccion transaccion_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.transaccion
    ADD CONSTRAINT transaccion_pkey PRIMARY KEY (transaccion);
 F   ALTER TABLE ONLY public.transaccion DROP CONSTRAINT transaccion_pkey;
       public            postgres    false    219            �           2606    31140    turno turno_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.turno
    ADD CONSTRAINT turno_pkey PRIMARY KEY (turno);
 :   ALTER TABLE ONLY public.turno DROP CONSTRAINT turno_pkey;
       public            postgres    false    227            �           2606    31024    usuario usuario_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (usuario);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    212            �           2606    31064    egreso egreso_tipo_egreso_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.egreso
    ADD CONSTRAINT egreso_tipo_egreso_fkey FOREIGN KEY (tipo_egreso) REFERENCES public.tipo_egreso(tipo_egreso) NOT VALID;
 H   ALTER TABLE ONLY public.egreso DROP CONSTRAINT egreso_tipo_egreso_fkey;
       public          postgres    false    215    217    2977            �           2606    31146    empleado empleado_cargo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_cargo_fkey FOREIGN KEY (cargo) REFERENCES public.cargo(cargo) NOT VALID;
 F   ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_cargo_fkey;
       public          postgres    false    225    223    2985            �           2606    31141    empleado empleado_persona_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_persona_fkey FOREIGN KEY (persona) REFERENCES public.persona(persona) NOT VALID;
 H   ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_persona_fkey;
       public          postgres    false    202    223    2963            �           2606    31151    empleado empleado_turno_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.empleado
    ADD CONSTRAINT empleado_turno_fkey FOREIGN KEY (turno) REFERENCES public.turno(turno) NOT VALID;
 F   ALTER TABLE ONLY public.empleado DROP CONSTRAINT empleado_turno_fkey;
       public          postgres    false    2987    223    227            �           2606    31095    ingreso ingreso_producto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.ingreso
    ADD CONSTRAINT ingreso_producto_fkey FOREIGN KEY (producto) REFERENCES public.producto(producto) NOT VALID;
 G   ALTER TABLE ONLY public.ingreso DROP CONSTRAINT ingreso_producto_fkey;
       public          postgres    false    204    221    2965            �           2606    39248    persona persona_genero_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_genero_fkey FOREIGN KEY (genero) REFERENCES public.genero(genero) NOT VALID;
 E   ALTER TABLE ONLY public.persona DROP CONSTRAINT persona_genero_fkey;
       public          postgres    false    2989    202    229            �           2606    31025 $   producto producto_tipo_producto_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.producto
    ADD CONSTRAINT producto_tipo_producto_fkey FOREIGN KEY (tipo_producto) REFERENCES public.tipo_producto(tipo_producto) NOT VALID;
 N   ALTER TABLE ONLY public.producto DROP CONSTRAINT producto_tipo_producto_fkey;
       public          postgres    false    204    210    2971            �           2606    31105    egreso proveedor    FK CONSTRAINT     |   ALTER TABLE ONLY public.egreso
    ADD CONSTRAINT proveedor FOREIGN KEY (proveedor) REFERENCES public.proveedor(proveedor);
 :   ALTER TABLE ONLY public.egreso DROP CONSTRAINT proveedor;
       public          postgres    false    206    2967    215            �           2606    31030 &   tipo_producto tipo_producto_marca_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT tipo_producto_marca_fkey FOREIGN KEY (marca) REFERENCES public.marca(marca) NOT VALID;
 P   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT tipo_producto_marca_fkey;
       public          postgres    false    200    210    2961            �           2606    31035 (   tipo_producto tipo_producto_tamaño_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.tipo_producto
    ADD CONSTRAINT "tipo_producto_tamaño_fkey" FOREIGN KEY (tamanho) REFERENCES public.tamanho(tamanho) NOT VALID;
 T   ALTER TABLE ONLY public.tipo_producto DROP CONSTRAINT "tipo_producto_tamaño_fkey";
       public          postgres    false    2969    208    210            �           2606    31080 #   transaccion transaccion_egreso_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.transaccion
    ADD CONSTRAINT transaccion_egreso_fkey FOREIGN KEY (egreso) REFERENCES public.egreso(egreso) NOT VALID;
 M   ALTER TABLE ONLY public.transaccion DROP CONSTRAINT transaccion_egreso_fkey;
       public          postgres    false    2975    215    219            �           2606    31100    egreso usuario    FK CONSTRAINT     t   ALTER TABLE ONLY public.egreso
    ADD CONSTRAINT usuario FOREIGN KEY (usuario) REFERENCES public.usuario(usuario);
 8   ALTER TABLE ONLY public.egreso DROP CONSTRAINT usuario;
       public          postgres    false    215    212    2973            �           2606    31040    usuario usuario_persona_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_persona_fkey FOREIGN KEY (persona) REFERENCES public.persona(persona) NOT VALID;
 F   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_persona_fkey;
       public          postgres    false    212    2963    202            V      x������ � �      L      x������ � �      T      x������ � �      Z   !   x�3��uv�����2�ts�u�1c���� `d.      R      x������ � �      =      x������ � �      ?   P   x�3�t+JMI�tJ,J�,�笨��442� �?8�2�v��tt��tr�q
u�s䌈��41122�4���4)����� �4      A      x������ � �      C      x������ � �      E      x������ � �      N      x������ � �      G      x������ � �      P      x������ � �      X      x������ � �      I   8   x�3�LJ-�H��L��/�4�L+JMI�2��M,Ʉs�8��J2��9�L�=... J��     
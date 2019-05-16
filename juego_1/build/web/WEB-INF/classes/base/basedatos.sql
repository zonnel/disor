/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  eliel
 * Created: 10/05/2019
 */

create table Tipo(
id INTEGER PRIMARY KEY NOT null generated always as identity (start with 1, INCREMENT BY 1),
descripcion varchar(50)
);

create table Palabra(
idPalabra INTEGER PRIMARY KEY NOT null generated always as identity (start with 1, INCREMENT BY 1),
palabra VARCHAR(30) NOT NULL,
tipo integer not null,
nivel integer not null,
foreign key (tipo) references Tipo(id)
);

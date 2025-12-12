/*
 * Class: SWE2511 - React Filter
 *
 * Web Application entry point
 */

import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.css';

import App from './App.jsx';

const text = "Lorem ipsum dolor sit amet consectetur adipiscing elit Mauris lacus mi ultrices quis " +
    "lacus porttitor sodales dapibus nunc Proin ultrices ipsum sit amet lorem bibendum ornare Sed at velit " +
    "eget erat ullamcorper facilisis Phasellus ac felis mattis scelerisque libero at facilisis erat Integer " +
    "congue dui ornare justo pulvinar commodo Cras vitae scelerisque sapien Nullam vel urna augue Aliquam " +
    "augue orci convallis eget elementum non luctus in est In quis sollicitudin velit Aenean malesuada " +
    "mauris eros in efficitur quam vestibulum sit amet Suspendisse eros felis varius eu mollis non " +
    "condimentum et ligula Nunc bibendum quis sapien sit amet sagittis Nulla sagittis libero sed vulputate " +
    "congue Aliquam elit nisl scelerisque et sapien sit amet varius rhoncus tortor Maecenas vel lectus " +
    "pretium volutpat enim nec mollis nulla Nam justo lacus molestie id egestas vitae tincidunt vitae " +
    "neque Mauris justo mi sodales sit amet faucibus at molestie et velit Phasellus semper tellus sed " +
    "laoreet consectetur mauris tortor placerat tortor eget vulputate enim erat eu tellus Nullam pharetra " +
    "ornare lobortis Nullam tincidunt arcu vitae consequat porta purus nulla dictum elit at euismod augue " +
    "tellus vitae nunc Nunc scelerisque vestibulum velit ac sodales Nulla sit amet magna sagittis vehicula " +
    "mauris vitae pulvinar lacus Integer ut nulla facilisis vestibulum dolor non posuere ligula Sed iaculis " +
    "sit amet metus et faucibus Donec dictum ullamcorper lorem eu finibus Duis maximus ante id efficitur " +
    "facilisis augue mauris imperdiet justo at molestie nisl lacus sit amet justo Quisque nec ex condimentum " +
    "auctor tortor nec hendrerit est Class aptent taciti sociosqu ad litora torquent per conubia nostra per " +
    "inceptos himenaeos Integer vulputate sodales dignissim Donec nec quam porta nunc bibendum euismod " +
    "Maecenas tempus nulla nec orci consectetur vitae dictum ligula auctor Ut accumsan aliquet sapien in" +
    " ullamcorper sapien ultricies eget Vestibulum ante ipsum primis in faucibus orci luctus et ultrices " +
    "posuere cubilia";

/**
 * Initialize the app and render the application
 */
ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App text={text} />
    </React.StrictMode>
);

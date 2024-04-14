import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Destination from './destination';
import PlaceCategory from './place-category';
import Place from './place';
import TourCategory from './tour-category';
import Tour from './tour';
import TourStep from './tour-step';
import Promotion from './promotion';
import TourSchedule from './tour-schedule';
import TourExtraCategory from './tour-extra-category';
import TourExtra from './tour-extra';
import Driver from './driver';
import Vehicle from './vehicle';
import Booking from './booking';
import Passenger from './passenger';
import ImageFile from './image-file';
import WebPage from './web-page';
import Menu from './menu';
import Language from './language';
import Content from './content';
import Tag from './tag';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="destination/*" element={<Destination />} />
        <Route path="place-category/*" element={<PlaceCategory />} />
        <Route path="place/*" element={<Place />} />
        <Route path="tour-category/*" element={<TourCategory />} />
        <Route path="tour/*" element={<Tour />} />
        <Route path="tour-step/*" element={<TourStep />} />
        <Route path="promotion/*" element={<Promotion />} />
        <Route path="tour-schedule/*" element={<TourSchedule />} />
        <Route path="tour-extra-category/*" element={<TourExtraCategory />} />
        <Route path="tour-extra/*" element={<TourExtra />} />
        <Route path="driver/*" element={<Driver />} />
        <Route path="vehicle/*" element={<Vehicle />} />
        <Route path="booking/*" element={<Booking />} />
        <Route path="passenger/*" element={<Passenger />} />
        <Route path="image-file/*" element={<ImageFile />} />
        <Route path="web-page/*" element={<WebPage />} />
        <Route path="menu/*" element={<Menu />} />
        <Route path="language/*" element={<Language />} />
        <Route path="content/*" element={<Content />} />
        <Route path="tag/*" element={<Tag />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

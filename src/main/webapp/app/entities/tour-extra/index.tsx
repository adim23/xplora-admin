import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourExtra from './tour-extra';
import TourExtraDetail from './tour-extra-detail';
import TourExtraUpdate from './tour-extra-update';
import TourExtraDeleteDialog from './tour-extra-delete-dialog';

const TourExtraRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourExtra />} />
    <Route path="new" element={<TourExtraUpdate />} />
    <Route path=":id">
      <Route index element={<TourExtraDetail />} />
      <Route path="edit" element={<TourExtraUpdate />} />
      <Route path="delete" element={<TourExtraDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourExtraRoutes;

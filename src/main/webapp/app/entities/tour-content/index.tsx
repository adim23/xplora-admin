import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourContent from './tour-content';
import TourContentDetail from './tour-content-detail';
import TourContentUpdate from './tour-content-update';
import TourContentDeleteDialog from './tour-content-delete-dialog';

const TourContentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourContent />} />
    <Route path="new" element={<TourContentUpdate />} />
    <Route path=":id">
      <Route index element={<TourContentDetail />} />
      <Route path="edit" element={<TourContentUpdate />} />
      <Route path="delete" element={<TourContentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourContentRoutes;

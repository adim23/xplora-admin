import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TourExtraCategory from './tour-extra-category';
import TourExtraCategoryDetail from './tour-extra-category-detail';
import TourExtraCategoryUpdate from './tour-extra-category-update';
import TourExtraCategoryDeleteDialog from './tour-extra-category-delete-dialog';

const TourExtraCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TourExtraCategory />} />
    <Route path="new" element={<TourExtraCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<TourExtraCategoryDetail />} />
      <Route path="edit" element={<TourExtraCategoryUpdate />} />
      <Route path="delete" element={<TourExtraCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TourExtraCategoryRoutes;

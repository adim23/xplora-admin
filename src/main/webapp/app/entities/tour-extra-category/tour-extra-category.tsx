import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './tour-extra-category.reducer';

export const TourExtraCategory = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const tourExtraCategoryList = useAppSelector(state => state.tourExtraCategory.entities);
  const loading = useAppSelector(state => state.tourExtraCategory.loading);
  const totalItems = useAppSelector(state => state.tourExtraCategory.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="tour-extra-category-heading" data-cy="TourExtraCategoryHeading">
        <Translate contentKey="xploraAdminApp.tourExtraCategory.home.title">Tour Extra Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="xploraAdminApp.tourExtraCategory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/tour-extra-category/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xploraAdminApp.tourExtraCategory.home.createLabel">Create new Tour Extra Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tourExtraCategoryList && tourExtraCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.code">Code</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('icon')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.icon">Icon</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('icon')} />
                </th>
                <th className="hand" onClick={sort('defaultImage')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.defaultImage">Default Image</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImage')} />
                </th>
                <th className="hand" onClick={sort('defaultImageData')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.defaultImageData">Default Image Data</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('defaultImageData')} />
                </th>
                <th className="hand" onClick={sort('enabled')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.enabled">Enabled</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('enabled')} />
                </th>
                <th className="hand" onClick={sort('shopCategoryId')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.shopCategoryId">Shop Category Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shopCategoryId')} />
                </th>
                <th className="hand" onClick={sort('shopUrl')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.shopUrl">Shop Url</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('shopUrl')} />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createdDate')} />
                </th>
                <th>
                  <Translate contentKey="xploraAdminApp.tourExtraCategory.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tourExtraCategoryList.map((tourExtraCategory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tour-extra-category/${tourExtraCategory.id}`} color="link" size="sm">
                      {tourExtraCategory.id}
                    </Button>
                  </td>
                  <td>{tourExtraCategory.code}</td>
                  <td>{tourExtraCategory.icon}</td>
                  <td>{tourExtraCategory.defaultImage}</td>
                  <td>
                    {tourExtraCategory.defaultImageData ? (
                      <div>
                        {tourExtraCategory.defaultImageDataContentType ? (
                          <a onClick={openFile(tourExtraCategory.defaultImageDataContentType, tourExtraCategory.defaultImageData)}>
                            <img
                              src={`data:${tourExtraCategory.defaultImageDataContentType};base64,${tourExtraCategory.defaultImageData}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {tourExtraCategory.defaultImageDataContentType}, {byteSize(tourExtraCategory.defaultImageData)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{tourExtraCategory.enabled ? 'true' : 'false'}</td>
                  <td>{tourExtraCategory.shopCategoryId}</td>
                  <td>{tourExtraCategory.shopUrl}</td>
                  <td>
                    {tourExtraCategory.createdDate ? (
                      <TextFormat type="date" value={tourExtraCategory.createdDate} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tourExtraCategory.createdBy ? tourExtraCategory.createdBy.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/tour-extra-category/${tourExtraCategory.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tour-extra-category/${tourExtraCategory.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/tour-extra-category/${tourExtraCategory.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="xploraAdminApp.tourExtraCategory.home.notFound">No Tour Extra Categories found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tourExtraCategoryList && tourExtraCategoryList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default TourExtraCategory;
